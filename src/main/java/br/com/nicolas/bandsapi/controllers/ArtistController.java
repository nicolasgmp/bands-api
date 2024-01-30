package br.com.nicolas.bandsapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.bandsapi.client.artist.dto.ArtistResponse;
import br.com.nicolas.bandsapi.maps.ArtistMapper;
import br.com.nicolas.bandsapi.services.ArtistService;

@RestController
@RequestMapping("/spotify/api")
public class ArtistController {

    private ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artists/getSpotify/{id}")
    public ResponseEntity<ArtistResponse> getArtistSpotify(@PathVariable String id) {
        return ResponseEntity.ok(artistService.findArtistSpotify(id));
    }

    @GetMapping("/artists/getDB/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable String id) {
        return ResponseEntity.ok(ArtistMapper.fromArtistToResponse(artistService.findArtistById(id)));
    }

    @PostMapping("/artists/{id}")
    public ResponseEntity<ArtistResponse> saveArtist(@PathVariable String id, @RequestParam("market") String market) {
        return ResponseEntity.ok(artistService.saveArtist(id, market));
    }
}
