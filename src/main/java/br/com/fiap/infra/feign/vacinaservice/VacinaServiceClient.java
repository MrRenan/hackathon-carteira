package br.com.fiap.infra.feign.vacinaservice;

import br.com.fiap.features.carteira.application.port.VacinaServicePort;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vacina-service", url = "${url.vacina-service}")
public interface VacinaServiceClient extends VacinaServicePort {

    @GetMapping("/{nome}")
    VacinaClientResponse obterVacinaPorNome(@PathVariable String nome);

}