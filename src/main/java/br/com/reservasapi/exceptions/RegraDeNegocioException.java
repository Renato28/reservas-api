package br.com.reservasapi.exceptions;

public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensage) {
        super(mensage);
    }
}
