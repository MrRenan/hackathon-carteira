package br.com.fiap.features.carteira.application.usecase.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record IncluirVacinaAplicadaUseCaseRequest(
        String cpf,
        VacinaUseCaseRequest vacina
) {

    @Builder
    public record VacinaUseCaseRequest(
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