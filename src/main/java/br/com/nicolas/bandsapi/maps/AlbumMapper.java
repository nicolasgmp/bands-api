package br.com.nicolas.bandsapi.maps;

import java.util.List;
import java.util.stream.Collectors;

import br.com.nicolas.bandsapi.client.album.dto.AlbumResponse;
import br.com.nicolas.bandsapi.client.album.dto.AlbumSpotify;
import br.com.nicolas.bandsapi.models.Album;
import br.com.nicolas.bandsapi.models.Track;

public class AlbumMapper {

    private AlbumMapper() {
    }

    public static AlbumResponse fromAlbumToResponse(Album album) {
        return new AlbumResponse(
                album.getName(), album.getArtistName(), album.getRecordCompany(),
                album.getReleaseDate(), album.getTracks());
    }

    public static AlbumResponse fromSpotifyToResponse(AlbumSpotify album) {
        List<String> tracksName = album.tracks().items().stream().map(Track::getName).collect(Collectors.toList());

        return new AlbumResponse(
                album.name(), album.artists().get(0).getName(),
                album.label(), album.releaseDate(), tracksName);
    }
}
