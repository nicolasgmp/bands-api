package br.com.nicolas.bandsapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.album.AlbumClient;
import br.com.nicolas.bandsapi.client.album.AlbumResponse;
import br.com.nicolas.bandsapi.model.Album;
import br.com.nicolas.bandsapi.model.Artist;
import br.com.nicolas.bandsapi.model.Track;
import br.com.nicolas.bandsapi.repositories.AlbumRepository;
import jakarta.transaction.Transactional;

@Service
public class AlbumService {

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

    @Transactional
    public Album saveAlbum(String id) {
        String token = loginService.loginSpotify();
        AlbumResponse albumResponse = albumClient.getAlbum(BEARER + token, id);

        String artistId = albumResponse.artists().get(0).getId();
        Artist artist = artistService.findArtistById(artistId);

        if (artist == null) {
            artist = artistService.saveArtist(artistId, "BR");
        }

        List<String> albumTracksName = albumResponse.tracks().items().stream()
                .map(Track::getName)
                .collect(Collectors.toList());

        var newAlbum = new Album(
                albumResponse.id(), albumResponse.name(), albumResponse.releaseDate(), albumResponse.albumType(),
                albumResponse.label(), albumResponse.genres(), artist.getName(), albumTracksName, artist);

        albumRepository.save(newAlbum);
        artistService.addNewAlbum(artist.getId(), newAlbum);

        return newAlbum;
    }
}
