package br.com.nicolas.bandsapi.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import br.com.nicolas.bandsapi.services.exceptions.AlbumNotFoundException;
import br.com.nicolas.bandsapi.services.exceptions.ArtistNotFoundException;
import br.com.nicolas.bandsapi.services.exceptions.BadFormatIdException;
import br.com.nicolas.bandsapi.services.exceptions.NullIdException;
import br.com.nicolas.bandsapi.services.exceptions.SpotifyAuthException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NullIdException.class)
    public ResponseEntity<StandardError> handleNullIdException(NullIdException ex, HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<StandardError> handleAlbumNotFoundException(AlbumNotFoundException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SpotifyAuthException.class)
    public ResponseEntity<StandardError> handleSpotifyAuthException(SpotifyAuthException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BadFormatIdException.class)
    public ResponseEntity<StandardError> handleBadFormatIdException(BadFormatIdException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<StandardError> handleArtistNotFoundException(ArtistNotFoundException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<StandardError> handleNoResourcerFoundException(NoResourceFoundException ex,
            HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
