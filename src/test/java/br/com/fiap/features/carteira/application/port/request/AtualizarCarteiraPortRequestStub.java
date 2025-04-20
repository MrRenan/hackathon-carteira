package br.com.fiap.features.carteira.application.port.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AtualizarCarteiraPortRequestStub {

    protected static final Faker faker = new Faker();

    public static AtualizarCarteiraPortRequestStub novo() {
        return new AtualizarCarteiraPortRequestStub();
    }

    public AtualizarCarteiraPortRequest build() {
        return AtualizarCarteiraPortRequest.builder()
                .cpf(faker.number().digits(11))
                .paciente(AtualizarCarteiraPortRequest.PacientePortRequest.builder()
                        .nomeCompleto(faker.name().fullName())
                        .build())
                .vacinas(List.of(AtualizarCarteiraPortRequest.VacinaPortRequest.builder()
                        .nome(faker.name().fullName())
                        .build()))
                .build();
    }

}