package br.com.nicolas.bandsapi.services.exceptions;

public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(String message) {
        super(message);
    }
}
