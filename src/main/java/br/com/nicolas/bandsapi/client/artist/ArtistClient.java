package br.com.nicolas.bandsapi.client.artist;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.nicolas.bandsapi.client.album.AlbumResponse;

@FeignClient(name = "ArtistClient", url = "https://api.spotify.com")
public interface ArtistClient {

    @GetMapping(value = "/v1/artists/{id}")
    ArtistResponse getArtist(@RequestHeader("Authorization") String authorization, @PathVariable String id);

    @GetMapping(value = "/v1/artists/{id}/albums")
    AlbumResponse getArtistAlbums(@RequestHeader("Authorization") String authorization, @PathVariable String id);
}
