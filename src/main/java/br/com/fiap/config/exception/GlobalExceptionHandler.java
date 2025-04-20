package br.com.fiap.config.exception;

import br.com.fiap.features.carteira.domain.exception.*;
import br.com.fiap.features.carteira.domain.exception.dto.SimpleError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarteiraCadastradaException.class)
    public ResponseEntity<SimpleError> handleCarteiraCadastradoException(CarteiraCadastradaException ex) {
        return ResponseEntity.status(CONFLICT).body(new SimpleError(ex.getMessage(), CONFLICT.toString()));
    }

    @ExceptionHandler(CarteiraNaoEncontradaException.class)
    public ResponseEntity<SimpleError> handleCarteiraNaoEncontradoException(CarteiraNaoEncontradaException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new SimpleError(ex.getMessage(), NOT_FOUND.toString()));
    }

    @ExceptionHandler(DoseInvalidaException.class)
    public ResponseEntity<SimpleError> handleDoseInvalidaException(DoseInvalidaException ex) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new SimpleError(ex.getMessage(), UNPROCESSABLE_ENTITY.toString()));
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<SimpleError> handlePacienteNaoEncontradoException(PacienteNaoEncontradoException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new SimpleError(ex.getMessage(), NOT_FOUND.toString()));
    }

    @ExceptionHandler(VacinaNaoEncontradaException.class)
    public ResponseEntity<SimpleError> handleVacinaNaoEncontradaException(VacinaNaoEncontradaException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new SimpleError(ex.getMessage(), NOT_FOUND.toString()));
    }

    @ExceptionHandler(VacinaExistenteException.class)
    public ResponseEntity<SimpleError> handleVacinaExistenteException(VacinaExistenteException ex) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new SimpleError(ex.getMessage(), UNPROCESSABLE_ENTITY.toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SimpleError> handleTodasExcessoes(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new SimpleError("Ocorreu um erro interno: " + ex.getMessage(), INTERNAL_SERVER_ERROR.toString()));
    }

}