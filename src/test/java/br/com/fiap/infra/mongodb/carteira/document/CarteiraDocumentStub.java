package br.com.fiap.infra.mongodb.carteira.document;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CarteiraDocumentStub {

    protected static final Faker faker = new Faker();

    public static CarteiraDocumentStub novo() {
        return new CarteiraDocumentStub();
    }

    public CarteiraDocument build() {
        return CarteiraDocument.builder()
                .cpf(faker.number().digits(11))
                .paciente(CarteiraDocument.PacienteDocument.builder().build())
                .vacinas(List.of(CarteiraDocument.VacinaDocument.builder().build()))
                .build();
    }

}