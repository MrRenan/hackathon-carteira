package br.com.fiap.features.carteira.application.usecase.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CriarCarteiraUseCaseRequestStub {

    protected static final Faker faker = new Faker();

    public static CriarCarteiraUseCaseRequestStub novo() {
        return new CriarCarteiraUseCaseRequestStub();
    }

    public CriarCarteiraUseCaseRequest build() {
        return CriarCarteiraUseCaseRequest.builder()
                .cpf(faker.number().digits(11))
                .vacinas(List.of(CriarCarteiraUseCaseRequest.VacinaUseCaseRequest.builder()
                        .nome(faker.name().fullName())
                        .dataAplicacao(LocalDateTime.now())
                        .doseAplicada(faker.number().numberBetween(1, 3))
                        .build()))
                .build();
    }

}