package br.com.nicolas.bandsapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.bandsapi.client.album.dto.AlbumResponse;
import br.com.nicolas.bandsapi.maps.AlbumMapper;
import br.com.nicolas.bandsapi.services.AlbumService;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    private AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums/spotify/{id}")
    public ResponseEntity<AlbumResponse> getAlbumSpotify(@PathVariable String id) {
        return ResponseEntity.ok(albumService.findAlbumSpotify(id));
    }

    @GetMapping("/albums/DB/{id}")
    public ResponseEntity<AlbumResponse> getAlbumDB(@PathVariable String id) {
        return ResponseEntity.ok(AlbumMapper.fromAlbumToResponse(albumService.findAlbumById(id)));
    }

    @PostMapping("/albums/{id}")
    public ResponseEntity<AlbumResponse> saveAlbum(@PathVariable String id) {
        return ResponseEntity.ok(albumService.saveAlbum(id));
    }
}
