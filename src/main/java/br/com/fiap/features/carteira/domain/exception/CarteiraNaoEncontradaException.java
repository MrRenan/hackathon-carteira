package br.com.fiap.features.carteira.domain.exception;

public class CarteiraNaoEncontradaException extends RuntimeException {

    public CarteiraNaoEncontradaException() {
        super("Carteira nao encontrada.");
    }

}