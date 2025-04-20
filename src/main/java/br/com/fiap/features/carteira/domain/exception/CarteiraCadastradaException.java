package br.com.fiap.features.carteira.domain.exception;

public class CarteiraCadastradaException extends RuntimeException {

    public CarteiraCadastradaException() {
        super("Carteira possui cadastro.");
    }

}