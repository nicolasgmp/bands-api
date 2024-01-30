package br.com.nicolas.bandsapi.maps;

import java.util.Collections;
import java.util.List;

import br.com.nicolas.bandsapi.client.artist.dto.ArtistResponse;
import br.com.nicolas.bandsapi.client.artist.dto.ArtistSpotify;
import br.com.nicolas.bandsapi.models.Artist;

public class ArtistMapper {

    private ArtistMapper() {
    }

    public static ArtistResponse fromSpotifyToResponse(ArtistSpotify artist, List<String> albumsName,
            List<String> topTracks) {

        return new ArtistResponse(artist.name(), artist.followers().total(), artist.popularity(), albumsName,
                artist.genres(), topTracks);
    }

    public static ArtistResponse fromArtistToResponse(Artist artist) {
        return new ArtistResponse(artist.getName(), artist.getFollowers(), artist.getPopularity(),
                artist.getAlbumsName(), artist.getGenres(), artist.getTopTracks());
    }

    public static Artist fromResponseToArtist(ArtistResponse artist, String artistId) {
        return new Artist(artistId, artist.name(), artist.followers(), artist.popularity(), artist.genres(),
                artist.albumsName(), artist.topTracks(), Collections.emptyList());
    }
}
