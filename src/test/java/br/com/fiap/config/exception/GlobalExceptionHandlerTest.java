package br.com.fiap.config.exception;

import br.com.fiap.features.carteira.domain.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    @DisplayName("Deve retornar status 500 INTERNAL SERVER ERROR quando Exception for lançada")
    void test01() {
        // ASSETS
        var ex = new RuntimeException("Erro genérico");
        var mensagemEsperada = "Ocorreu um erro interno: ".concat(ex.getMessage());

        // ACTION
        var response = handler.handleTodasExcessoes(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(INTERNAL_SERVER_ERROR.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 409 CONFLICT quando CarteiraCadastradaException for lançada")
    void test02() {
        // ASSETS
        var mensagemEsperada = "Carteira possui cadastro.";
        var ex = new CarteiraCadastradaException();

        // ACTION
        var response = handler.handleCarteiraCadastradoException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(CONFLICT, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(CONFLICT.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 404 NOT_FOUND quando CarteiraNaoEncontradaException for lançada")
    void test03() {
        // ASSETS
        var mensagemEsperada = "Carteira nao encontrada.";
        var ex = new CarteiraNaoEncontradaException();

        // ACTION
        var response = handler.handleCarteiraNaoEncontradoException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(NOT_FOUND, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(NOT_FOUND.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 422 UNPROCESSABLE_ENTITY quando DoseInvalidaException for lançada")
    void test04() {
        // ASSETS
        var mensagemEsperada = "Dose aplicada maior que o total de doses.";
        var ex = new DoseInvalidaException();

        // ACTION
        var response = handler.handleDoseInvalidaException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(UNPROCESSABLE_ENTITY, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(UNPROCESSABLE_ENTITY.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 404 NOT_FOUND quando PacienteNaoEncontradoException for lançada")
    void test05() {
        // ASSETS
        var mensagemEsperada = "Paciente nao encontrado.";
        var ex = new PacienteNaoEncontradoException();

        // ACTION
        var response = handler.handlePacienteNaoEncontradoException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(NOT_FOUND, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(NOT_FOUND.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 404 NOT_FOUND quando VacinaNaoEncontradaException for lançada")
    void test06() {
        // ASSETS
        var mensagemEsperada = "Vacina não encontrada: VacinaX";
        var ex = new VacinaNaoEncontradaException(mensagemEsperada);

        // ACTION
        var response = handler.handleVacinaNaoEncontradaException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(NOT_FOUND, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(NOT_FOUND.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

    @Test
    @DisplayName("Deve retornar status 422 UNPROCESSABLE_ENTITY quando VacinaExistenteException for lançada")
    void test07() {
        // ASSETS
        var mensagemEsperada = "Vacina já existente.";
        var ex = new VacinaExistenteException();

        // ACTION
        var response = handler.handleVacinaExistenteException(ex);

        // ASSERTIONS
        assertAll(
                () -> assertEquals(UNPROCESSABLE_ENTITY, response.getStatusCode()),
                () -> assertEquals(mensagemEsperada, Objects.requireNonNull(response.getBody()).message()),
                () -> assertEquals(UNPROCESSABLE_ENTITY.toString(), Objects.requireNonNull(response.getBody()).code())
        );
    }

}