package br.com.nicolas.bandsapi.client.artist.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.nicolas.bandsapi.models.Track;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TopTracksResponse(List<Track> tracks) {

}
