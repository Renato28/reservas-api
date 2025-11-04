package br.com.reservasapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensage) {
        super(mensage);
    }
}
