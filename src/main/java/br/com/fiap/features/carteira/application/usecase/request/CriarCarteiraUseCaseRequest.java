package br.com.fiap.features.carteira.application.usecase.request;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CriarCarteiraUseCaseRequest(
        String cpf,
        List<VacinaUseCaseRequest> vacinas
) {

    @Builder
    public record VacinaUseCaseRequest(
            String nome,
            int doseAplicada,
            LocalDateTime dataAplicacao
    ) {

    }

}