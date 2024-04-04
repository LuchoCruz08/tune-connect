package com.tuneconnect.Repository;
import com.tuneconnect.Entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByUsername(String username);

    @Query("SELECT a FROM Artist a WHERE a.artist_name LIKE %:name%")
    List<Artist> findByName(@Param("name") String name);

    List<Artist> findByMusicalGenre(String musicalGenre);

}
