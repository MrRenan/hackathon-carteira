package br.com.fiap.features.carteira.domain.exception;

public class DoseInvalidaException extends RuntimeException {

    public DoseInvalidaException() {
        super("Dose aplicada maior que o total de doses.");
    }

}