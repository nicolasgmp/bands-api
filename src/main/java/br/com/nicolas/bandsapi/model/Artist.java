package br.com.nicolas.bandsapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_artists")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Artist {

    @Id
    @Column(name = "artist_id")
    private String id;

    @Column(name = "artist_name")
    private String name;

    @Column(name = "artist_followers")
    private Integer followers;

    @Column(name = "artist_popularity")
    private Integer popularity;

    @CollectionTable(name = "artist_genres")
    @Column(name = "artist_genres")
    private List<String> genres = new ArrayList<>();

    @Column(name = "albums_name")
    private List<String> albumsName = new ArrayList<>();

    @Column(name = "top_tracks")
    private List<String> topTracks = new ArrayList<>();

    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

}
