package br.com.fiap.features.carteira.application.usecase.response;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CarteiraUseCaseResponseStub {

    protected static final Faker faker = new Faker();

    public static CarteiraUseCaseResponseStub novo() {
        return new CarteiraUseCaseResponseStub();
    }

    public CarteiraUseCaseResponse build() {
        return CarteiraUseCaseResponse.builder()
                .cpf(faker.number().digits(11))
                .paciente(CarteiraUseCaseResponse.PacienteUseCaseResponse.builder()
                        .nomeCompleto(faker.name().fullName())
                        .dataNascimento(faker.date().birthday().toString())
                        .email(faker.internet().emailAddress())
                        .telefone(faker.phoneNumber().phoneNumber())
                        .build())
                .vacinas(List.of(CarteiraUseCaseResponse.VacinaUseCaseResponse.builder()
                        .dataAplicacao(LocalDateTime.of(2020, 1, 1, 0, 0))
                        .grupo(faker.lorem().word())
                        .doses(faker.number().numberBetween(2, 4))
                        .intervaloEntreDosesEmDias(faker.number().numberBetween(60, 90))
                        .fabricante(faker.company().name())
                        .nome(faker.lorem().word())
                        .descricao(faker.lorem().paragraph())
                        .doseAplicada(faker.number().numberBetween(1, 2))
                        .build()))
                .build();
    }

}