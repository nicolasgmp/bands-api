package br.com.nicolas.bandsapi.client.album;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AlbumClient", url = "https://api.spotify.com")
public interface AlbumClient {

    @GetMapping(value = "/v1/albums/{id}")
    AlbumResponse getAlbum(@RequestHeader("Authorization") String authorization, @PathVariable String id);

    @GetMapping(value = "/v1/albums/{id}/tracks")
    AlbumTracksResponse getAlbumTracks(@RequestHeader("Authorization") String authorization, @PathVariable String id);

}
