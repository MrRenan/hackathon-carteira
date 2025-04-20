package br.com.fiap.features.carteira.domain.exception;

public class VacinaNaoEncontradaException extends RuntimeException {

    public VacinaNaoEncontradaException(String nome) {
        super(nome);
    }

}
