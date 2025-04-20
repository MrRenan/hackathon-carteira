package br.com.fiap.features.carteira.domain.exception;

public class VacinaExistenteException extends RuntimeException {

    public VacinaExistenteException() {
        super("Vacina já existente.");
    }

}