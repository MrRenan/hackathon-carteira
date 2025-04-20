package br.com.fiap.features.carteira.application.usecase;

import br.com.fiap.features.carteira.application.mapper.CarteiraApplicationMapper;
import br.com.fiap.features.carteira.application.port.CarteiraPort;
import br.com.fiap.features.carteira.application.port.PacienteServicePort;
import br.com.fiap.features.carteira.application.port.VacinaServicePort;
import br.com.fiap.features.carteira.application.usecase.request.AtualizarVacinaAplicadaUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.request.CriarCarteiraUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.request.IncluirVacinaAplicadaUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.request.RemoverCarteiraUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.response.CarteiraUseCaseResponse;
import br.com.fiap.features.carteira.domain.exception.*;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponse;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

@RequiredArgsConstructor
@Component("carteiraUseCase")
public class CarteiraUseCase {

    private final CarteiraApplicationMapper mapper;
    private final CarteiraPort port;
    private final PacienteServicePort carteiraServicePort;
    private final VacinaServicePort vacinaServicePort;

    public CarteiraUseCaseResponse executarCriarCarteira(CriarCarteiraUseCaseRequest useCaseRequest) {
        try {
            var carteiraPortResponse = port.buscarCarteiraPorCpf(useCaseRequest.cpf());
            if (carteiraPortResponse != null) {
                throw new CarteiraCadastradaException();
            }
        } catch (CarteiraNaoEncontradaException ignorado) {
        }

        var paciente = this.obterPacientePorCpf(useCaseRequest.cpf());
        var vacinas = obterVacinas(useCaseRequest.vacinas());

        var portRequest = mapper.paraCriarCarteiraPortRequest(paciente, vacinas);
        var portResponse = port.criarCarteira(portRequest);
        return mapper.paraCarteiraUseCaseResponse(portResponse);
    }

    public CarteiraUseCaseResponse executarBuscarCarteiraPorCpf(String cpf) {
        var portResponse = port.buscarCarteiraPorCpf(cpf);
        return mapper.paraCarteiraUseCaseResponse(portResponse);
    }

    public List<CarteiraUseCaseResponse> executarListarTodasCarteiras() {
        var portResponse = port.listarTodasCarteiras();
        return portResponse.stream().map(mapper::paraCarteiraUseCaseResponse).toList();
    }

    public CarteiraUseCaseResponse executarAtualizarVacinaAplicada(AtualizarVacinaAplicadaUseCaseRequest useCaseRequest) {
        var carteira = port.buscarCarteiraPorCpf(useCaseRequest.cpf());
        var vacinaEncontrada = carteira.vacinas().stream()
                .filter(vacina -> vacina.nome().equalsIgnoreCase(useCaseRequest.vacina().nome()))
                .findFirst()
                .orElseThrow(() -> new VacinaNaoEncontradaException(useCaseRequest.vacina().nome()));
        if (useCaseRequest.vacina().doseAplicada() > vacinaEncontrada.doses()) {
            throw new DoseInvalidaException();
        }
        var vacinas = carteira.vacinas().stream()
                .filter(vacina -> !vacina.nome().equalsIgnoreCase(useCaseRequest.vacina().nome()))
                .toList();
        var vacinaAtualizada = mapper.paraVacinaPortResponse(vacinaEncontrada, useCaseRequest.vacina());
        var listaVacinas = concat(vacinas.stream(), Stream.of(vacinaAtualizada)).toList();
        var carteiraAtualizada = mapper.paraCarteiraAtualizada(carteira, useCaseRequest, listaVacinas);
        var portRequest = mapper.paraAtualizarCarteiraPortRequest(carteiraAtualizada);
        var portResponse = port.atualizarCarteira(portRequest);
        return mapper.paraCarteiraUseCaseResponse(portResponse);

    }

    public CarteiraUseCaseResponse executarIncluirVacinaAplicada(IncluirVacinaAplicadaUseCaseRequest useCaseRequest) {

        var carteira = port.buscarCarteiraPorCpf(useCaseRequest.cpf());
        if (carteira.vacinas().stream()
                .anyMatch(v -> v.nome().equalsIgnoreCase(useCaseRequest.vacina().nome()))) {
            throw new VacinaExistenteException();
        }
        var vacina = vacinaServicePort.obterVacinaPorNome(useCaseRequest.vacina().nome());
        if (useCaseRequest.vacina().doseAplicada() > vacina.doses()) {
            throw new DoseInvalidaException();
        }
        var novaVacina = mapper.paraVacinaPortResponse(vacina, useCaseRequest.vacina());
        var listaVacinas = concat(carteira.vacinas().stream(), Stream.of(novaVacina)).toList();

        var carteiraAtualizada = mapper.paraCarteiraPortResponse(carteira, listaVacinas);
        var portRequest = mapper.paraAtualizarCarteiraPortRequest(carteiraAtualizada);
        var portResponse = port.atualizarCarteira(portRequest);
        return mapper.paraCarteiraUseCaseResponse(portResponse);

    }

    public void executarRemoverCarteira(RemoverCarteiraUseCaseRequest useCaseRequest) {
        port.removerCarteira(useCaseRequest.cpf());
    }

    private PacienteClientResponse obterPacientePorCpf(String cpf) {

        var paciente = carteiraServicePort.obterPacientePorCpf(cpf);
        if (paciente == null) {
            throw new PacienteNaoEncontradoException();
        }
        return paciente;

    }

    private List<VacinaClientResponse> obterVacinas(List<CriarCarteiraUseCaseRequest.VacinaUseCaseRequest> vacinas) {
        List<VacinaClientResponse> vacinasAplicadas = new ArrayList<>();

        for (var vacinaRequest : vacinas) {
            var vacinaInfo = obterVacinaPorNome(vacinaRequest.nome());
            var vacinaAplicada = VacinaClientResponse.builder()
                    .doseAplicada(vacinaRequest.doseAplicada())
                    .dataAplicacao(vacinaRequest.dataAplicacao())
                    .nome(vacinaInfo.nome())
                    .grupo(vacinaInfo.grupo())
                    .intervaloEntreDosesEmDias(vacinaInfo.intervaloEntreDosesEmDias())
                    .descricao(vacinaInfo.descricao())
                    .doses(vacinaInfo.doses())
                    .fabricante(vacinaInfo.fabricante())
                    .build();
            vacinasAplicadas.add(vacinaAplicada);
        }

        return vacinasAplicadas;

    }

    private VacinaClientResponse obterVacinaPorNome(String nome) {

        var vacinaPortResponse = vacinaServicePort.obterVacinaPorNome(nome);
        if (vacinaPortResponse == null) {
            throw new VacinaNaoEncontradaException("Vacina não encontrada: " + nome);
        }

        return vacinaPortResponse;
    }

}