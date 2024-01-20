package br.com.nicolas.bandsapi.client.album;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.nicolas.bandsapi.model.Album;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AlbumResponse(List<Album> items) {
}
