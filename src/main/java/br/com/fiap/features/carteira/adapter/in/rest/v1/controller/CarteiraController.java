package br.com.fiap.features.carteira.adapter.in.rest.v1.controller;

import br.com.fiap.features.carteira.adapter.in.rest.v1.api.CarteiraApi;
import br.com.fiap.features.carteira.adapter.in.rest.v1.mapper.CarteiraInRestMapper;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.AtualizarVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.CriarCarteiraRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.IncluirVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.response.CarteiraResponse;
import br.com.fiap.features.carteira.application.usecase.CarteiraUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class CarteiraController implements CarteiraApi {

    private final CarteiraInRestMapper mapper;
    private final CarteiraUseCase useCase;

    @Override
    public CarteiraResponse criarCarteira(CriarCarteiraRequest request) {
        var useCaseRequest = mapper.paraCriarCarteiraUseCaseRequest(request);
        var useCaseResponse = useCase.executarCriarCarteira(useCaseRequest);
        return mapper.paraCarteiraResponse(useCaseResponse);
    }

    @Override
    public CarteiraResponse buscarCarteiraPorCpf(String cpf) {
        var useCaseResponse = useCase.executarBuscarCarteiraPorCpf(cpf);
        return mapper.paraCarteiraResponse(useCaseResponse);
    }

    @Override
    public List<CarteiraResponse> listarTodasCarteiras() {
        var useCaseResponse = useCase.executarListarTodasCarteiras();
        return useCaseResponse.stream().map(mapper::paraCarteiraResponse).toList();
    }

    @Override
    public CarteiraResponse atualizarVacinaAplicada(String cpf, AtualizarVacinaAplicadaRequest request) {
        var useCaseRequest = mapper.paraAtualizarVacinaAplicadaUseCaseRequest(cpf, request);
        var useCaseResponse = useCase.executarAtualizarVacinaAplicada(useCaseRequest);
        return mapper.paraCarteiraResponse(useCaseResponse);
    }

    @Override
    public CarteiraResponse incluirVacinaAplicada(String cpf, IncluirVacinaAplicadaRequest request) {
        var useCaseRequest = mapper.paraIncluirVacinaAplicadaUseCaseRequest(cpf, request);
        var useCaseResponse = useCase.executarIncluirVacinaAplicada(useCaseRequest);
        return mapper.paraCarteiraResponse(useCaseResponse);
    }

    @Override
    public void removerCarteira(String cpf) {
        var useCaseRequest = mapper.paraRemoverCarteiraUseCaseRequest(cpf);
        useCase.executarRemoverCarteira(useCaseRequest);
    }

}