package br.com.nicolas.bandsapi.client.artist;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ArtistResponse(
        String id,
        String name,
        Followers followers,
        List<String> genres) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Followers(String href, int total) {
    }
}
