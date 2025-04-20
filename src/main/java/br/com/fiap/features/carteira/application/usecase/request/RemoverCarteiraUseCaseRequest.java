package br.com.fiap.features.carteira.application.usecase.request;

import lombok.Builder;

@Builder
public record RemoverCarteiraUseCaseRequest(
        String cpf
) {

}