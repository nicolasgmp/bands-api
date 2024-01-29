package br.com.nicolas.bandsapi.services;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.artist.ArtistAlbumsResponse;
import br.com.nicolas.bandsapi.client.artist.ArtistClient;
import br.com.nicolas.bandsapi.client.artist.ArtistResponse;
import br.com.nicolas.bandsapi.client.artist.TopTracksResponse;
import br.com.nicolas.bandsapi.model.Album;
import br.com.nicolas.bandsapi.model.Artist;
import br.com.nicolas.bandsapi.model.Track;
import br.com.nicolas.bandsapi.repositories.ArtistRepository;
import jakarta.transaction.Transactional;

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

    public ArtistResponse findArtistSpotify(String id) {
        String token = loginService.loginSpotify();
        return artistClient.getArtist(BEARER + token, id);
    }

    public Artist findArtistById(String id) {
        if (id != null) {
            return artistRepository.findById(id).orElse(null);
        }

        return null;
    }

    @Transactional
    public Artist saveArtist(String id, String market) {
        String token = loginService.loginSpotify();

        ArtistResponse artistResponse = artistClient.getArtist(BEARER + token, id);

        Artist newArtist = new Artist(artistResponse.id(), artistResponse.name(), artistResponse.followers().total(),
                artistResponse.popularity(), artistResponse.genres(), new ArrayList<>(), new ArrayList<>(),
                Collections.emptyList());

        saveArtistAlbumsName(id, token, newArtist);
        saveArtistTopTracks(id, market, token, newArtist);

        return artistRepository.save(newArtist);
    }

    @Transactional
    public Artist addNewAlbum(String id, Album album) {
        Artist artist = findArtistById(id);
        artist.getAlbums().add(album);
        return artistRepository.save(artist);
    }

    public void saveArtistAlbumsName(String id, String token, Artist artist) {
        ArtistAlbumsResponse artistAlbumsResponse = artistClient.getArtistAlbums(BEARER + token, id);

        artistAlbumsResponse.items().stream()
                .map(Album::getName)
                .forEach(artist.getAlbumsName()::add);
    }

    public void saveArtistTopTracks(String id, String market, String token, Artist artist) {
        TopTracksResponse topTracksResponse = artistClient.getArtistTopTracks(BEARER + token, id, market);

        topTracksResponse.tracks().stream()
                .map(Track::getName)
                .forEach(artist.getTopTracks()::add);
    }
}
