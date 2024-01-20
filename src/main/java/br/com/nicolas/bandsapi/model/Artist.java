package br.com.nicolas.bandsapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String id;

    private String name;

    private Integer followers;

    private List<String> genres = new ArrayList<>();

    private List<String> albumsName = new ArrayList<>();

}
