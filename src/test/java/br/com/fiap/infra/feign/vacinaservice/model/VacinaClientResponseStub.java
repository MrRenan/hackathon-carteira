package br.com.fiap.infra.feign.vacinaservice.model;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class VacinaClientResponseStub {

    protected static final Faker faker = new Faker();
    private String nome = faker.name().fullName();

    public static VacinaClientResponseStub novo() {
        return new VacinaClientResponseStub();
    }

    public VacinaClientResponse build() {
        return VacinaClientResponse.builder()
                .nome(nome)
                .grupo(faker.number().digits(1))
                .doses(faker.number().numberBetween(2, 4))
                .intervaloEntreDosesEmDias(faker.number().numberBetween(60, 90))
                .descricao(faker.lorem().paragraph())
                .dataAplicacao(LocalDateTime.now())
                .fabricante(faker.company().name())
                .doseAplicada(faker.number().numberBetween(1, 3))
                .build();
    }

    public VacinaClientResponseStub nome(String nome) {
        this.nome = nome;
        return this;
    }


}