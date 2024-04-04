package com.tuneconnect.DTO.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuneconnect.Entity.Album;
import com.tuneconnect.Entity.Song;
import java.time.LocalDate;
import java.util.List;

public record AlbumResponseDTO(
        String title,
        String genre,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        List<Song> songs
) {

    public AlbumResponseDTO(Album album) {
        this(String.valueOf(album.getAlbumId()), album.getAlbumTitle(), album.getAlbumDate(), album.getAlbumSongs());
    }

}
