package br.com.fiap.features.carteira.application.port.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CarteiraPortResponse(
        String cpf,
        PacientePortResponse paciente,
        List<VacinaPortResponse> vacinas
) {

    @Builder
    public record PacientePortResponse(
            String nomeCompleto,
            String dataNascimento,
            String telefone,
            String email
    ) {

    }

    @Builder
    public record VacinaPortResponse(
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