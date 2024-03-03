package br.com.nicolas.bandsapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.album.AlbumClient;
import br.com.nicolas.bandsapi.client.album.dto.AlbumResponse;
import br.com.nicolas.bandsapi.client.album.dto.AlbumSpotify;
import br.com.nicolas.bandsapi.maps.AlbumMapper;
import br.com.nicolas.bandsapi.maps.ArtistMapper;
import br.com.nicolas.bandsapi.models.Album;
import br.com.nicolas.bandsapi.models.Artist;
import br.com.nicolas.bandsapi.models.Track;
import br.com.nicolas.bandsapi.repositories.AlbumRepository;
import br.com.nicolas.bandsapi.services.exceptions.AlbumNotFoundException;
import br.com.nicolas.bandsapi.services.exceptions.BadFormatIdException;
import br.com.nicolas.bandsapi.services.exceptions.NullIdException;
import feign.FeignException;
import jakarta.transaction.Transactional;

@Service
public class AlbumService {

    private static final String ALBUM_NOT_FOUND = "Album not found!";
    private static final String BEARER = "Bearer ";
    private AlbumRepository albumRepository;
    private AlbumClient albumClient;
    private LoginService loginService;
    private ArtistService artistService;

    public AlbumService(AlbumRepository albumRepository, AlbumClient albumClient, LoginService loginService,
            ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.albumClient = albumClient;
        this.loginService = loginService;
        this.artistService = artistService;
    }

    public AlbumResponse findAlbumSpotify(String id) {
        checkIdNullOrEmpty(id);

        try {
            String token = loginService.loginSpotify();
            return AlbumMapper.fromSpotifyToResponse(albumClient.getAlbum(BEARER + token, id).get());
        } catch (FeignException ex) {
            throw new AlbumNotFoundException(ALBUM_NOT_FOUND);
        }
    }

    public Album findAlbumById(String id) {
        checkIdNullOrEmpty(id);

        return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(ALBUM_NOT_FOUND));
    }

    @Transactional
    public AlbumResponse saveAlbum(String id) {
        checkIdNullOrEmpty(id);

        try {
            String token = loginService.loginSpotify();
            AlbumSpotify albumResponse = albumClient.getAlbum(BEARER + token, id).get();
            String artistId = albumResponse.artists().get(0).getId();
            Artist artist = artistService.findArtistById(artistId);

            if (artist == null) {
                artist = ArtistMapper.fromResponseToArtist(artistService.saveArtist(artistId, "BR"), artistId);
            }

            List<String> albumTracksName = getAlbumTracksName(id, token);

            var newAlbum = new Album(
                    albumResponse.id(), albumResponse.name(), albumResponse.releaseDate(), albumResponse.albumType(),
                    albumResponse.label(), albumResponse.genres(), artist.getName(), albumTracksName, artist);

            albumRepository.save(newAlbum);
            artistService.addNewAlbum(artist.getId(), newAlbum);

            return AlbumMapper.fromAlbumToResponse(newAlbum);
        } catch (FeignException.BadRequest ex) {
            throw new BadFormatIdException("Badly formatted Id!");
        } catch (FeignException ex) {
            throw new AlbumNotFoundException(ALBUM_NOT_FOUND);
        }
    }

    private List<String> getAlbumTracksName(String id, String token) {
        AlbumSpotify albumResponse = albumClient.getAlbum(BEARER + token, id).get();

        return albumResponse.tracks().items().stream()
                .map(Track::getName)
                .collect(Collectors.toList());
    }

    private void checkIdNullOrEmpty(String id) {
        if (id == null || id.isEmpty()) {
            throw new NullIdException("Id cannot be null!");
        }
    }
}
