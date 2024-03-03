package br.com.nicolas.bandsapi.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.artist.ArtistClient;
import br.com.nicolas.bandsapi.client.artist.dto.ArtistAlbumsResponse;
import br.com.nicolas.bandsapi.client.artist.dto.ArtistResponse;
import br.com.nicolas.bandsapi.client.artist.dto.ArtistSpotify;
import br.com.nicolas.bandsapi.client.artist.dto.TopTracksResponse;
import br.com.nicolas.bandsapi.maps.ArtistMapper;
import br.com.nicolas.bandsapi.models.Album;
import br.com.nicolas.bandsapi.models.Artist;
import br.com.nicolas.bandsapi.models.Track;
import br.com.nicolas.bandsapi.repositories.ArtistRepository;
import br.com.nicolas.bandsapi.services.exceptions.ArtistNotFoundException;
import br.com.nicolas.bandsapi.services.exceptions.NullIdException;
import feign.FeignException;
import jakarta.transaction.Transactional;

@Service
public class ArtistService {

    private static final String ARTIST_NOT_FOUND = "Artist not found!";
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
        checkIdNullOrEmpty(id);

        try {
            String token = loginService.loginSpotify();
            ArtistSpotify artistSpotify = artistClient.getArtist(BEARER + token, id);
            List<String> artistAlbumsName = getArtistAlbumsName(id, token);
            List<String> artistTopTracks = getArtistTopTracks(id, "BR", token);

            return ArtistMapper.fromSpotifyToResponse(artistSpotify, artistAlbumsName, artistTopTracks);
        } catch (FeignException ex) {
            throw new ArtistNotFoundException(ARTIST_NOT_FOUND);
        }
    }

    public Artist findArtistById(String id) {
        checkIdNullOrEmpty(id);

        try {
            return artistRepository.findById(id).orElse(null);
        } catch (ArtistNotFoundException ex) {
            throw new ArtistNotFoundException(ARTIST_NOT_FOUND);
        }
    }

    @Transactional
    public ArtistResponse saveArtist(String id, String market) {
        checkIdNullOrEmpty(id);

        try {
            String token = loginService.loginSpotify();

            ArtistSpotify artistResponse = artistClient.getArtist(BEARER + token, id);
            List<String> artistAlbumsName = getArtistAlbumsName(id, token);
            List<String> artistTopTracks = getArtistTopTracks(id, market, token);

            Artist newArtist = new Artist(
                    artistResponse.id(), artistResponse.name(), artistResponse.followers().total(),
                    artistResponse.popularity(), artistResponse.genres(), artistAlbumsName,
                    artistTopTracks, Collections.emptyList());

            artistRepository.save(newArtist);

            return ArtistMapper.fromArtistToResponse(newArtist);
        } catch (FeignException ex) {
            throw new ArtistNotFoundException(ARTIST_NOT_FOUND);
        }
    }

    @Transactional
    public ArtistResponse addNewAlbum(String id, Album album) {
        Artist artist = findArtistById(id);
        artist.getAlbums().add(album);
        artistRepository.save(artist);

        return ArtistMapper.fromArtistToResponse(artist);
    }

    private List<String> getArtistAlbumsName(String id, String token) {
        ArtistAlbumsResponse artistAlbumsResponse = artistClient.getArtistAlbums(BEARER + token, id);

        return artistAlbumsResponse.items().stream()
                .map(Album::getName)
                .collect(Collectors.toList());
    }

    private List<String> getArtistTopTracks(String id, String market, String token) {
        TopTracksResponse topTracksResponse = artistClient.getArtistTopTracks(BEARER + token, id, market);

        return topTracksResponse.tracks().stream()
                .map(Track::getName)
                .collect(Collectors.toList());
    }

    private void checkIdNullOrEmpty(String id) {
        if (id == null || id.isEmpty()) {
            throw new NullIdException("Id cannot be null");
        }
    }
}
