package br.com.fiap.infra.feign.pacienteservice.model;

import lombok.Builder;

@Builder
public record PacienteClientResponse(
        String nomeCompleto,
        String cpf,
        String dataNascimento,
        String telefone,
        String email
) {
}