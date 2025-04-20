package br.com.fiap.features.carteira.domain.exception.dto;

import lombok.Builder;

@Builder
public record SimpleError (
        String message,
        String code
) {
}
