package br.com.nicolas.bandsapi.client.artist.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ArtistSpotify(
        String id,
        String name,
        Followers followers,
        Integer popularity,
        List<String> genres) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Followers(int total) {
    }
}
