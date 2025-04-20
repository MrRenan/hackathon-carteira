package br.com.fiap.infra.feign.vacinaservice.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VacinaClientResponse(
        String nome,
        String fabricante,
        Integer doseAplicada,
        Integer doses,
        Integer intervaloEntreDosesEmDias,
        String descricao,
        String grupo,
        LocalDateTime dataAplicacao
) {
}