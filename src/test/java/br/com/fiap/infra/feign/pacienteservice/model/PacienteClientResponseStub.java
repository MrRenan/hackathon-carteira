package br.com.fiap.infra.feign.pacienteservice.model;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PacienteClientResponseStub {

    protected static final Faker faker = new Faker();
    private String cpf = faker.number().digits(11);

    public static PacienteClientResponseStub novo() {
        return new PacienteClientResponseStub();
    }

    public PacienteClientResponse build() {
        return PacienteClientResponse.builder()
                .cpf(cpf)
                .email(faker.internet().emailAddress())
                .dataNascimento(faker.date().birthday().toString())
                .nomeCompleto(faker.name().fullName())
                .telefone(faker.phoneNumber().phoneNumber())
                .build();
    }

    public PacienteClientResponseStub cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

}