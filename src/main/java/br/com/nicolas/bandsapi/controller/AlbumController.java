package br.com.nicolas.bandsapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.bandsapi.services.AlbumService;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    private AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/albums/{id}")
    public ResponseEntity<String> saveAlbum(@PathVariable String id) {
        albumService.saveAlbum(id);
        return ResponseEntity.ok("Album saved with success");
    }
}
