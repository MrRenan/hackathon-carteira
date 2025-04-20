package br.com.fiap.infra.adapter.paciente;

import br.com.fiap.features.carteira.application.port.PacienteServicePort;
import br.com.fiap.infra.feign.pacienteservice.PacienteServiceClient;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("pacienteServiceAdapter")
public class PacienteServiceAdapter implements PacienteServicePort {

    private final PacienteServiceClient client;

    @Override
    public PacienteClientResponse obterPacientePorCpf(String cpf) {
        return client.obterPacientePorCpf(cpf);
    }

}