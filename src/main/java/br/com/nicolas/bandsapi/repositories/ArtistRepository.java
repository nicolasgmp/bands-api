package br.com.nicolas.bandsapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicolas.bandsapi.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {

}
