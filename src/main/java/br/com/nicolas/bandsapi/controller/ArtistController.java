package br.com.nicolas.bandsapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.bandsapi.client.artist.ArtistResponse;
import br.com.nicolas.bandsapi.services.ArtistService;

@RestController
@RequestMapping("/spotify/api")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistResponse> getArtist(@PathVariable String id) {
        var artist = artistService.getArtist(id);
        var artistResponse = new ArtistResponse(artist.id(), artist.name(), artist.followers(), artist.genres());
        return ResponseEntity.ok(artistResponse);
    }

    @PostMapping("/artists/{id}")
    public ResponseEntity<String> saveArtist(@PathVariable String id) {
        artistService.saveArtist(id);
        return ResponseEntity.ok("Artist saved with success");
    }
}
