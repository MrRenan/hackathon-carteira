package br.com.fiap.infra.adapter.paciente;

import br.com.fiap.infra.feign.pacienteservice.PacienteServiceClient;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponseStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PacienteServiceAdapterTest {

    @InjectMocks
    private PacienteServiceAdapter adapter;
    @Mock
    private PacienteServiceClient client;


    @Nested
    @DisplayName("Porta de obter paciente por cpf")
    class ObterPacientePorCpf {

        @Test
        @DisplayName("Deve executar porta de obter paciente por cpf com sucesso")
        void test01(){
            // ASSETS
            var cpf = "1234567891";
            var clientResponse = PacienteClientResponseStub.novo().build();
            Mockito.when(client.obterPacientePorCpf(cpf)).thenReturn(clientResponse);
            // ACTION
            var result = adapter.obterPacientePorCpf(cpf);
            // ASSERTIONS
            Assertions.assertThat(result).isEqualTo(clientResponse);
        }
    }

}