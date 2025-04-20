package br.com.fiap.features.carteira.application.port.request;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AtualizarCarteiraPortRequest(
        String cpf,
        PacientePortRequest paciente,
        List<VacinaPortRequest> vacinas
) {

    @Builder
    public record PacientePortRequest(
            String nomeCompleto,
            String dataNascimento,
            String telefone,
            String email
    ) {

    }

    @Builder
    public record VacinaPortRequest(
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