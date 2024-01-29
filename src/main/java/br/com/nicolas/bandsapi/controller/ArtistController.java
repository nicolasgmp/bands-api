package br.com.nicolas.bandsapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.bandsapi.client.artist.ArtistResponse;
import br.com.nicolas.bandsapi.model.Artist;
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
        ArtistResponse artist = artistService.findArtistSpotify(id);
        return ResponseEntity.ok(new ArtistResponse(
                artist.id(), artist.name(), artist.followers(),
                artist.popularity(), artist.genres()));
    }

    @GetMapping("/artists/getDB/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable String id) {
        return ResponseEntity.ok(artistService.findArtistById(id));
    }

    @PostMapping("/artists/{id}")
    public ResponseEntity<String> saveArtist(@PathVariable String id, @RequestParam("market") String market) {
        artistService.saveArtist(id, market);
        return ResponseEntity.ok("Artist saved with success");
    }
}
