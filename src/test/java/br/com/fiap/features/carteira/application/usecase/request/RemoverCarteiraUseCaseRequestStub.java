package br.com.fiap.features.carteira.application.usecase.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RemoverCarteiraUseCaseRequestStub {

    protected static final Faker faker = new Faker();

    public static RemoverCarteiraUseCaseRequestStub novo() {
        return new RemoverCarteiraUseCaseRequestStub();
    }

    public RemoverCarteiraUseCaseRequest build() {
        return RemoverCarteiraUseCaseRequest.builder()
                .cpf(faker.number().digits(11))
                .build();
    }

}