package br.com.fiap.features.carteira.application.port.response;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CarteiraPortResponseStub {

    protected static final Faker faker = new Faker();

    public static CarteiraPortResponseStub novo() {
        return new CarteiraPortResponseStub();
    }

    public CarteiraPortResponse build() {
        return CarteiraPortResponse.builder()
                .cpf(faker.number().digits(11))
                .paciente(CarteiraPortResponse.PacientePortResponse.builder()
                        .nomeCompleto(faker.name().fullName())
                        .build())
                .vacinas(List.of(CarteiraPortResponse.VacinaPortResponse.builder()
                        .nome(faker.name().fullName())
                        .doses(3)
                        .doseAplicada(1)
                        .build()))
                .build();
    }

}