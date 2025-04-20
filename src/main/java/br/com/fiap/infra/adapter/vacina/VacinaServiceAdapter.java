package br.com.fiap.infra.adapter.vacina;

import br.com.fiap.features.carteira.application.port.VacinaServicePort;
import br.com.fiap.infra.feign.vacinaservice.VacinaServiceClient;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("vacinaServiceAdapter")
public class VacinaServiceAdapter implements VacinaServicePort {

    private final VacinaServiceClient client;

    @Override
    public VacinaClientResponse obterVacinaPorNome(String nome) {
        return client.obterVacinaPorNome(nome);
    }

}