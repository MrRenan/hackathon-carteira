package br.com.fiap.features.carteira.adapter.in.rest.v1.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AtualizarVacinaAplicadaRequestStub {

    protected static final Faker faker = new Faker();

    public static AtualizarVacinaAplicadaRequestStub novo() {
        return new AtualizarVacinaAplicadaRequestStub();
    }

    public AtualizarVacinaAplicadaRequest build() {
        return AtualizarVacinaAplicadaRequest.builder()
                .dataAplicacao(LocalDateTime.of(2020, 1, 1, 0, 0))
                .nome(faker.lorem().word())
                .doseAplicada(faker.number().numberBetween(1, 3))
                .build();
    }

}