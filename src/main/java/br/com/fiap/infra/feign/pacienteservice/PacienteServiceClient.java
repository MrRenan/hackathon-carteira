package br.com.fiap.infra.feign.pacienteservice;

import br.com.fiap.features.carteira.application.port.PacienteServicePort;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "paciente-service", url = "${url.paciente-service}")
public interface PacienteServiceClient extends PacienteServicePort {

    @GetMapping("/{cpf}")
    PacienteClientResponse obterPacientePorCpf(@PathVariable String cpf);

}
