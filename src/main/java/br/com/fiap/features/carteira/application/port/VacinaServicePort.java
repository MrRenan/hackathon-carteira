package br.com.fiap.features.carteira.application.port;

import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponse;

public interface VacinaServicePort {

    VacinaClientResponse obterVacinaPorNome(String nome);

}
