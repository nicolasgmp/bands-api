package br.com.nicolas.bandsapi.services.exceptions;

public class NullIdException extends RuntimeException {

    public NullIdException(String message) {
        super(message);
    }
}
