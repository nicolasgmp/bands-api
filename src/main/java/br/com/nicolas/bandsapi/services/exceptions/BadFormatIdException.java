package br.com.nicolas.bandsapi.services.exceptions;

public class BadFormatIdException extends RuntimeException {

    public BadFormatIdException(String message) {
        super(message);
    }
}
