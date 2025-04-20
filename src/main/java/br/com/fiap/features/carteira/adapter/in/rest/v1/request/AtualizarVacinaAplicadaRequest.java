package br.com.fiap.features.carteira.adapter.in.rest.v1.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AtualizarVacinaAplicadaRequest(
        @NotBlank
        String nome,
        @NotBlank
        int doseAplicada,
        @NotBlank
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
        LocalDateTime dataAplicacao) {

}