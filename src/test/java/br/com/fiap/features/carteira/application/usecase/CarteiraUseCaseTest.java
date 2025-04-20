package br.com.fiap.features.carteira.application.usecase;

import br.com.fiap.features.carteira.application.mapper.CarteiraApplicationMapper;
import br.com.fiap.features.carteira.application.port.CarteiraPort;
import br.com.fiap.features.carteira.application.port.PacienteServicePort;
import br.com.fiap.features.carteira.application.port.VacinaServicePort;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequestStub;
import br.com.fiap.features.carteira.application.port.response.CarteiraPortResponseStub;
import br.com.fiap.features.carteira.application.usecase.request.AtualizarVacinaAplicadaUseCaseRequestStub;
import br.com.fiap.features.carteira.application.usecase.request.CriarCarteiraUseCaseRequestStub;
import br.com.fiap.features.carteira.application.usecase.request.IncluirVacinaAplicadaUseCaseRequestStub;
import br.com.fiap.features.carteira.application.usecase.request.RemoverCarteiraUseCaseRequestStub;
import br.com.fiap.features.carteira.application.usecase.response.CarteiraUseCaseResponseStub;
import br.com.fiap.features.carteira.domain.exception.CarteiraCadastradaException;
import br.com.fiap.features.carteira.domain.exception.CarteiraNaoEncontradaException;
import br.com.fiap.features.carteira.domain.exception.DoseInvalidaException;
import br.com.fiap.features.carteira.domain.exception.VacinaExistenteException;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponseStub;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponseStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Carteira / Carteira UseCase")
class CarteiraUseCaseTest {

    private static final String CPF = "12345678901";

    @InjectMocks
    private CarteiraUseCase useCase;
    @Mock
    private CarteiraPort port;
    @Mock
    private PacienteServicePort pacienteServicePort;
    @Mock
    private VacinaServicePort vacinaServicePort;
    @Spy
    private CarteiraApplicationMapper mapper = getMapper(CarteiraApplicationMapper.class);

    @Nested
    @DisplayName("Caso de uso de criar carteira")
    class ExecutarCriarCarteira {

        @Test
        @DisplayName("Deve executar caso de uso de criar carteira com sucesso")
        void test01() {
            // ASSETS
            var useCaseRequest = CriarCarteiraUseCaseRequestStub.novo().build();
            var portResponse = CarteiraPortResponseStub.novo().build();
            var pacienteResponse = PacienteClientResponseStub.novo().cpf(CPF).build();
            var vacinaRequest = useCaseRequest.vacinas().getFirst();
            var vacinaResponse = VacinaClientResponseStub.novo().nome(vacinaRequest.nome()).build();
            var criarPortRequest = CriarCarteiraPortRequestStub.novo().build();
            var criarPortResponse = CarteiraPortResponseStub.novo().build();
            var expectedUseCaseResponse = CarteiraUseCaseResponseStub.novo().build();

            doThrow(new CarteiraNaoEncontradaException()).when(port).buscarCarteiraPorCpf(anyString());
            when(port.criarCarteira(any())).thenReturn(portResponse);
            when(pacienteServicePort.obterPacientePorCpf(any())).thenReturn(pacienteResponse);
            when(vacinaServicePort.obterVacinaPorNome(vacinaRequest.nome())).thenReturn(vacinaResponse);
            when(mapper.paraCriarCarteiraPortRequest(any(), any())).thenReturn(criarPortRequest);
            when(port.criarCarteira(criarPortRequest)).thenReturn(criarPortResponse);
            when(mapper.paraCarteiraUseCaseResponse(criarPortResponse)).thenReturn(expectedUseCaseResponse);

            // ACTION
            var response = useCase.executarCriarCarteira(useCaseRequest);

            // ASSERTIONS
            assertThat(response).usingRecursiveComparison().isEqualTo(expectedUseCaseResponse);
            verify(port).buscarCarteiraPorCpf(any());
            verify(pacienteServicePort).obterPacientePorCpf(any());
            verify(vacinaServicePort).obterVacinaPorNome(vacinaRequest.nome());
            verify(mapper).paraCriarCarteiraPortRequest(any(), anyList());
            verify(port).criarCarteira(criarPortRequest);
            verify(mapper, times(2)).paraCarteiraUseCaseResponse(criarPortResponse);
        }

        @Test
        @DisplayName("Deve executar caso de uso de criar carteira com erro, quando carteira já estiver cadastrada")
        void test02() {
            // ASSETS
            var useCaseRequest = CriarCarteiraUseCaseRequestStub.novo().build();
            var portResponse = CarteiraPortResponseStub.novo().build();

            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);

            // ACTION & ASSERTIONS
            org.junit.jupiter.api.Assertions.assertThrows(CarteiraCadastradaException.class,
                    () -> useCase.executarCriarCarteira(useCaseRequest));
            verify(port).buscarCarteiraPorCpf(any());
        }

    }

    @Nested
    @DisplayName("Caso de uso de buscar carteira por CPF")
    class ExecutarBuscarCarteiraPorCpfUseCase {

        @Test
        @DisplayName("Deve executar caso de uso de buscar carteira por CPF com sucesso")
        void test01() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            // ACTION
            var result = useCase.executarBuscarCarteiraPorCpf(CPF);
            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(portResponse);
            verify(mapper).paraCarteiraUseCaseResponse(portResponse);
        }

    }

    @Nested
    @DisplayName("Caso de uso de listar todas as carteiras")
    class ExecutarListarTodasCarteirasUseCase {

        @Test
        @DisplayName("Deve executar caso de uso de listar todas as carteiras com sucesso")
        void test01() {
            // ASSETS
            var portResponse = List.of(CarteiraPortResponseStub.novo().build());
            when(port.listarTodasCarteiras()).thenReturn(portResponse);
            // ACTION
            var result = useCase.executarListarTodasCarteiras();
            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(portResponse);
            verify(mapper, times(portResponse.size())).paraCarteiraUseCaseResponse(any());
        }

    }

    @Nested
    @DisplayName("Caso de uso de atualizar uma vacina aplicada")
    class ExecutarAtualizarVacinaAplicada {

        @Test
        @DisplayName("Deve executar caso de uso de atualizar uma vacina aplicada com sucesso")
        void test01() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            var vacina = portResponse.vacinas().stream().findFirst().get();
            var useCaseRequest = AtualizarVacinaAplicadaUseCaseRequestStub.novo().nome(vacina.nome()).build();

            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            when(port.atualizarCarteira(any())).thenReturn(portResponse);
            // ACTION
            var result = useCase.executarAtualizarVacinaAplicada(useCaseRequest);
            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(portResponse);
            verify(mapper).paraCarteiraUseCaseResponse(portResponse);
            verify(mapper).paraAtualizarCarteiraPortRequest(any());
        }

        @Test
        @DisplayName("Deve executar caso de uso de atualizar uma vacina aplicada com erro," +
                "quando dose inserida for maior que a dose cadastrada")
        void test02() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            var vacina = portResponse.vacinas().stream().findFirst().get();
            var useCaseRequest = AtualizarVacinaAplicadaUseCaseRequestStub.novo()
                    .nome(vacina.nome())
                    .doseAplicada(10).build();

            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            // ACTION & ASSERTIONS
            org.junit.jupiter.api.Assertions.assertThrows(DoseInvalidaException.class,
                    () -> useCase.executarAtualizarVacinaAplicada(useCaseRequest));

        }

    }

    @Nested
    @DisplayName("Caso de uso de incluir vacina aplicada")
    class ExecutarIncluirVacinaAplicada {

        @Test
        @DisplayName("Deve executar caso de uso de incluir vacina aplicada com sucesso")
        void test01() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            var vacina = CarteiraPortResponseStub.novo().build().vacinas().stream().findFirst().get();
            var useCaseRequest = IncluirVacinaAplicadaUseCaseRequestStub.novo().nome(vacina.nome()).build();
            var vacinaResponse = VacinaClientResponseStub.novo().build();

            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            when(port.atualizarCarteira(any())).thenReturn(portResponse);
            when(vacinaServicePort.obterVacinaPorNome(anyString())).thenReturn(vacinaResponse);
            // ACTION
            var result = useCase.executarIncluirVacinaAplicada(useCaseRequest);
            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(portResponse);
            verify(mapper).paraCarteiraUseCaseResponse(portResponse);
            verify(mapper).paraAtualizarCarteiraPortRequest(any());
        }

        @Test
        @DisplayName("Deve executar caso de uso de incluir vacina aplicada com erro," +
                "quando vacina já estiver cadastrada")
        void test02() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            var vacina = portResponse.vacinas().stream().findFirst().get();
            var useCaseRequest = IncluirVacinaAplicadaUseCaseRequestStub.novo()
                    .nome(vacina.nome())
                    .build();

            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            // ACTION & ASSERTIONS
            org.junit.jupiter.api.Assertions.assertThrows(VacinaExistenteException.class,
                    () -> useCase.executarIncluirVacinaAplicada(useCaseRequest));
        }

        @Test
        @DisplayName("Deve executar caso de uso de incluir vacina aplicada com erro," +
                "quando dose inserida for maior que a dose cadastrada")
        void test03() {
            // ASSETS
            var portResponse = CarteiraPortResponseStub.novo().build();
            var vacina = CarteiraPortResponseStub.novo().build().vacinas().stream().findFirst().get();
            var useCaseRequest = IncluirVacinaAplicadaUseCaseRequestStub.novo()
                    .nome(vacina.nome())
                    .doseAplicada(10)
                    .build();
            var vacinaResponse = VacinaClientResponseStub.novo().build();

            when(vacinaServicePort.obterVacinaPorNome(anyString())).thenReturn(vacinaResponse);
            when(port.buscarCarteiraPorCpf(any())).thenReturn(portResponse);
            // ACTION & ASSERTIONS
            org.junit.jupiter.api.Assertions.assertThrows(DoseInvalidaException.class,
                    () -> useCase.executarIncluirVacinaAplicada(useCaseRequest));
        }

    }

    @Nested
    @DisplayName("Caso de uso de remover carteira")
    class ExecutarRemoverCarteira {

        @Test
        @DisplayName("Deve executar caso de uso de remover carteira com sucesso")
        void test01() {
            // ASSETS
            var useCaseRequest = RemoverCarteiraUseCaseRequestStub.novo().build();
            doNothing().when(port).removerCarteira(any());
            // ACTION
            useCase.executarRemoverCarteira(useCaseRequest);
            // ASSERTIONS
            verify(port, times(1)).removerCarteira(useCaseRequest.cpf());
        }

    }

}