package br.com.fiap.infra.adapter.vacina;

import br.com.fiap.infra.feign.vacinaservice.VacinaServiceClient;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponseStub;
import com.github.javafaker.Faker;
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
class VacinaServiceAdapterTest {

    Faker faker = new Faker();
    @InjectMocks
    private VacinaServiceAdapter adapter;
    @Mock
    private VacinaServiceClient client;


    @Nested
    @DisplayName("Porta de obter vacina por nome")
    class ObterVacinaPorNome {

        @Test
        @DisplayName("Deve executar porta de obter paciente por cpf com sucesso")
        void test01() {
            // ASSETS
            var nome = faker.name().firstName();
            var clientResponse = VacinaClientResponseStub.novo().build();
            Mockito.when(client.obterVacinaPorNome(nome)).thenReturn(clientResponse);
            // ACTION
            var result = adapter.obterVacinaPorNome(nome);
            // ASSERTIONS
            Assertions.assertThat(result).isEqualTo(clientResponse);
        }
    }

}