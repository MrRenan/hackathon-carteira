package br.com.fiap.features.carteira.adapter.in.rest.v1.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CriarCarteiraRequestStub {

    protected static final Faker faker = new Faker();

    public static CriarCarteiraRequestStub novo() {
        return new CriarCarteiraRequestStub();
    }

    public CriarCarteiraRequest build() {
        return CriarCarteiraRequest.builder()
                .cpf(faker.number().digits(11))
                .vacinas(List.of(CriarCarteiraRequest.VacinaRequest.builder()
                        .dataAplicacao(LocalDateTime.of(2020, 1, 1, 0, 0))
                        .nome(faker.lorem().word())
                        .doseAplicada(faker.number().numberBetween(1, 3))
                        .build()))
                .build();
    }

}