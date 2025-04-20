package br.com.fiap.features.carteira.adapter.out.client;

import br.com.fiap.features.carteira.adapter.out.client.mapper.CarteiraOutMapper;
import br.com.fiap.features.carteira.application.port.CarteiraPort;
import br.com.fiap.features.carteira.application.port.request.AtualizarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.response.CarteiraPortResponse;
import br.com.fiap.features.carteira.domain.exception.CarteiraCadastradaException;
import br.com.fiap.features.carteira.domain.exception.CarteiraNaoEncontradaException;
import br.com.fiap.infra.mongodb.carteira.document.CarteiraDocument;
import br.com.fiap.infra.mongodb.carteira.repository.CarteiraMongoDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Component("carteiraAdapter")
public class CarteiraAdapter implements CarteiraPort {

    private final CarteiraOutMapper mapper;
    private final CarteiraMongoDBRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public CarteiraPortResponse criarCarteira(CriarCarteiraPortRequest request) {

        var carteiraDocument = mapper.paraCarteiraDocument(request);
        var buscarCarteiraPorCpf = buscarPorCpf(request.cpf());
        if (buscarCarteiraPorCpf.isPresent()) {
            throw new CarteiraCadastradaException();
        } else {
            var carteiraCriada = criarCarteira(carteiraDocument);
            return mapper.paraCarteiraPortResponse(carteiraCriada);
        }

    }

    @Override
    public CarteiraPortResponse buscarCarteiraPorCpf(String cpf) {

        var carteiraDocument = buscarPorCpf(cpf);
        if (carteiraDocument.isPresent()) {
            return mapper.paraCarteiraPortResponse(carteiraDocument.get());
        } else {
            throw new CarteiraNaoEncontradaException();
        }

    }

    @Override
    public List<CarteiraPortResponse> listarTodasCarteiras() {

        var todosCarteirasDocument = listarTodos();
        return todosCarteirasDocument.stream().map(mapper::paraCarteiraPortResponse).toList();

    }

    @Override
    public CarteiraPortResponse atualizarCarteira(AtualizarCarteiraPortRequest portRequest) {
        var carteiraAtualizado = atualizarCarteira(mapper.paraCarteiraDocument(portRequest));
        return mapper.paraCarteiraPortResponse(carteiraAtualizado);
    }

    @Override
    public void removerCarteira(String cpf) {
        var contagemCarteiraRemovido = mongoTemplate.remove(
                        query(where("cpf").is(cpf)),
                        CarteiraDocument.class)
                .getDeletedCount();

        if (contagemCarteiraRemovido == 0) {
            throw new CarteiraNaoEncontradaException();
        }
    }

    private CarteiraDocument atualizarCarteira(CarteiraDocument document) {

        var update = mapper.paraUpdate(document);

        var carteiraAtualizado = mongoTemplate.findAndModify(
                query(where("cpf").is(document.cpf())),
                update,
                FindAndModifyOptions.options().returnNew(true),
                CarteiraDocument.class
        );

        if (carteiraAtualizado == null) {
            throw new CarteiraNaoEncontradaException();
        }

        return carteiraAtualizado;

    }

    private CarteiraDocument criarCarteira(CarteiraDocument document) {
        return repository.save(document);
    }

    private Optional<CarteiraDocument> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    private List<CarteiraDocument> listarTodos() {
        return repository.findAll();
    }

}