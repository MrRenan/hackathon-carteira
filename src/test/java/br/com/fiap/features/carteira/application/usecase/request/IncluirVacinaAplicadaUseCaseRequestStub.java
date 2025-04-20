package br.com.fiap.features.carteira.application.usecase.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IncluirVacinaAplicadaUseCaseRequestStub {

    protected static final Faker faker = new Faker();
    private String nome = faker.name().firstName();
    private int doseAplicada = faker.number().numberBetween(1, 2);

    public static IncluirVacinaAplicadaUseCaseRequestStub novo() {
        return new IncluirVacinaAplicadaUseCaseRequestStub();
    }

    public IncluirVacinaAplicadaUseCaseRequest build() {
        return IncluirVacinaAplicadaUseCaseRequest.builder()
                .cpf(faker.number().digits(11))
                .vacina(IncluirVacinaAplicadaUseCaseRequest.VacinaUseCaseRequest.builder()
                        .nome(nome)
                        .dataAplicacao(LocalDateTime.of(2020, 1, 1, 0, 0))
                        .doseAplicada(doseAplicada)
                        .doses(3)
                        .build())
                .build();
    }

    public IncluirVacinaAplicadaUseCaseRequestStub nome(final String nome) {
        this.nome = nome;
        return this;
    }

    public IncluirVacinaAplicadaUseCaseRequestStub doseAplicada(final int doseAplicada) {
        this.doseAplicada = doseAplicada;
        return this;
    }

}