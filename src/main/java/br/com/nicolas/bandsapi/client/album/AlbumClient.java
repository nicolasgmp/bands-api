package br.com.nicolas.bandsapi.client.album;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.nicolas.bandsapi.client.album.dto.AlbumSpotify;
import br.com.nicolas.bandsapi.client.album.dto.AlbumTracksResponse;

@FeignClient(name = "AlbumClient", url = "https://api.spotify.com")
public interface AlbumClient {

    @GetMapping(value = "/v1/albums/{id}")
    Optional<AlbumSpotify> getAlbum(@RequestHeader("Authorization") String authorization, @PathVariable String id);

    @GetMapping(value = "/v1/albums/{id}/tracks")
    Optional<AlbumTracksResponse> getAlbumTracks(@RequestHeader("Authorization") String authorization,
            @PathVariable String id);

}
