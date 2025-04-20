package br.com.fiap.features.carteira.domain.exception;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException() {
        super("Paciente nao encontrado.");
    }

}
