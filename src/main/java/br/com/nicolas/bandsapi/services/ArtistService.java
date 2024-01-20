package br.com.nicolas.bandsapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.artist.ArtistClient;
import br.com.nicolas.bandsapi.client.artist.ArtistResponse;
import br.com.nicolas.bandsapi.model.Album;
import br.com.nicolas.bandsapi.model.Artist;
import br.com.nicolas.bandsapi.repositories.ArtistRepository;

@Service
public class ArtistService {

    private static final String BEARER = "Bearer ";
    private final ArtistClient artistClient;
    private final LoginService loginService;
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistClient artistClient, LoginService loginService, ArtistRepository artistRepository) {
        this.artistClient = artistClient;
        this.loginService = loginService;
        this.artistRepository = artistRepository;
    }

    public ArtistResponse getArtist(String id) {
        var token = loginService.loginSpotify();
        return artistClient.getArtist(BEARER + token, id);
    }

    public Artist saveArtist(String id) {
        var token = loginService.loginSpotify();
        var artistResponse = artistClient.getArtist(BEARER + token, id);
        var albumsResponse = artistClient.getArtistAlbums(BEARER + token, id);

        var newArtist = new Artist(artistResponse.id(), artistResponse.name(), artistResponse.followers().total(),
                artistResponse.genres(), new ArrayList<>());

        for (var album : albumsResponse.items()) {
            if (album.getAlbumType().equalsIgnoreCase("album")) {
                newArtist.getAlbumsName().add(album.getName());
            }
        }

        return artistRepository.save(newArtist);
    }

}
