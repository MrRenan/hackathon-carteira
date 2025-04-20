package br.com.fiap.features.carteira.application.usecase.request;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AtualizarVacinaAplicadaUseCaseRequestStub {

    protected static final Faker faker = new Faker();
    private String nome = faker.name().firstName();
    private int doseAplicada = faker.number().numberBetween(1, 2);

    public static AtualizarVacinaAplicadaUseCaseRequestStub novo() {
        return new AtualizarVacinaAplicadaUseCaseRequestStub();
    }

    public AtualizarVacinaAplicadaUseCaseRequest build() {
        return AtualizarVacinaAplicadaUseCaseRequest.builder()
                .cpf(faker.number().digits(11))
                .vacina(AtualizarVacinaAplicadaUseCaseRequest.VacinaUseCaseRequest.builder()
                        .nome(nome)
                        .dataAplicacao(LocalDateTime.of(2020, 1, 1, 0, 0))
                        .doseAplicada(doseAplicada)
                        .build())
                .build();
    }

    public AtualizarVacinaAplicadaUseCaseRequestStub nome(String nome) {
        this.nome = nome;
        return this;
    }

    public AtualizarVacinaAplicadaUseCaseRequestStub doseAplicada(int doseAplicada) {
        this.doseAplicada = doseAplicada;
        return this;
    }
}