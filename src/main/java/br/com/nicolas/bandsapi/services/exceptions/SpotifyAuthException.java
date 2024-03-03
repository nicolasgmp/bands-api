package br.com.nicolas.bandsapi.services.exceptions;

public class SpotifyAuthException extends RuntimeException {

    public SpotifyAuthException(String message) {
        super(message);
    }
}
