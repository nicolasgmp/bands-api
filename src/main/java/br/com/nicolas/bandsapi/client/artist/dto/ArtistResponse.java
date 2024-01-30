package br.com.nicolas.bandsapi.client.artist.dto;

import java.util.List;

public record ArtistResponse(
                String name, Integer followers,
                Integer popularity, List<String> albumsName,
                List<String> genres, List<String> topTracks) {
}
