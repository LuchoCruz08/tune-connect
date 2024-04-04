package com.tuneconnect.Repository;
import com.tuneconnect.Entity.Album;
import com.tuneconnect.Entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.artist.username = :username")
    Optional<List<Album>> findByUsername(@Param("username") String username);

    Album findByTitle(@Param("title") String albumTitle);

    @Query("SELECT s FROM Song s JOIN s.album a WHERE a.artist.artist_id = :artistId")
    Optional<List<Song>> findSongByArtistId(@Param("artistId") Long artistId);

}
