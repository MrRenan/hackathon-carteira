package br.com.fiap.features.carteira.application.port.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CriarCarteiraPortRequestStub {

    protected static final Faker faker = new Faker();

    public static CriarCarteiraPortRequestStub novo() {
        return new CriarCarteiraPortRequestStub();
    }

    public CriarCarteiraPortRequest build() {
        return CriarCarteiraPortRequest.builder()
                .cpf(faker.number().digits(11))
                .paciente(CriarCarteiraPortRequest.PacientePortRequest.builder()
                        .nomeCompleto(faker.name().fullName())
                        .build())
                .vacinas(List.of(CriarCarteiraPortRequest.VacinaPortRequest.builder()
                        .nome(faker.name().fullName())
                        .build()))
                .build();
    }

}