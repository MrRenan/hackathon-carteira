package br.com.fiap.features.carteira.adapter.in.rest.v1.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CarteiraResponse(
        String cpf,
        PacienteResponse paciente,
        List<VacinaResponse> vacinas
) {

    @Builder
    public record PacienteResponse(
            String nomeCompleto,
            String dataNascimento,
            String telefone,
            String email
    ) {

    }

    @Builder
    public record VacinaResponse(
            String nome,
            String fabricante,
            String descricao,
            Integer doseAplicada,
            Integer doses,
            Integer intervaloEntreDosesEmDias,
            String grupo,
            LocalDateTime dataAplicacao
    ) {

    }

}