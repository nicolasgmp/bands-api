package br.com.nicolas.bandsapi.client.album.dto;

import java.util.List;

public record AlbumResponse(
                String name, String artistName, String recordCompany,
                String releaseDate, List<String> tracks) {
}
