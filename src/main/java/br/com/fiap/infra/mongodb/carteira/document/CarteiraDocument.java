package br.com.fiap.infra.mongodb.carteira.document;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document(collection = "carteira")
public record CarteiraDocument(
        String cpf,
        PacienteDocument paciente,
        List<VacinaDocument> vacinas
) {

    @Builder
    public record PacienteDocument(
            String nomeCompleto,
            String dataNascimento,
            String telefone,
            String email
    ) {

    }

    @Builder
    public record VacinaDocument(
            String nome,
            String fabricante,
            String descricao,
            Integer doseAplicada,
            Integer doses,
            Integer intervaloEntreDosesEmDias,
            String grupo,
            String dataAplicacao
    ) {

    }

}