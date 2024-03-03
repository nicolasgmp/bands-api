package br.com.nicolas.bandsapi.services.exceptions;

public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(String message) {
        super(message);
    }
}
