package br.com.fiap.features.carteira.application.usecase.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CarteiraUseCaseResponse(
        String cpf,
        PacienteUseCaseResponse paciente,
        List<VacinaUseCaseResponse> vacinas
) {

    @Builder
    public record PacienteUseCaseResponse(
            String nomeCompleto,
            String dataNascimento,
            String telefone,
            String email
    ) {

    }

    @Builder
    public record VacinaUseCaseResponse(
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