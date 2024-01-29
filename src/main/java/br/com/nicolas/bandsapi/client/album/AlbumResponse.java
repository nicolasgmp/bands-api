package br.com.nicolas.bandsapi.client.album;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.nicolas.bandsapi.client.track.TrackWrapper;
import br.com.nicolas.bandsapi.model.Artist;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AlbumResponse(
        String id, String name, String releaseDate, String albumType, String label,
        List<String> genres, TrackWrapper tracks, List<Artist> artists) {
}
