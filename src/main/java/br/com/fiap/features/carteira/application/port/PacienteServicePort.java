package br.com.fiap.features.carteira.application.port;

import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponse;

public interface PacienteServicePort {

    PacienteClientResponse obterPacientePorCpf(String cpf);

}
