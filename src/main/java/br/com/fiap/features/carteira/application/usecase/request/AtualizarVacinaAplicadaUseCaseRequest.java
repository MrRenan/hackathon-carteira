package br.com.fiap.features.carteira.application.usecase.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AtualizarVacinaAplicadaUseCaseRequest(
        String cpf,
        VacinaUseCaseRequest vacina
) {

    @Builder
    public record VacinaUseCaseRequest(
            String nome,
            Integer doseAplicada,
            LocalDateTime dataAplicacao
    ) {

    }

}