package com.tuneconnect.DTO.Response;
import com.tuneconnect.Entity.Song;
import java.util.List;

public record SongResponseDTO(
        Long songId,
        String songTitle,
        String songGenre,
        String songLink,
        List<String> songImage,
        Long albumId
) {

    public SongResponseDTO(Song song) {
        this(song.getSongId(), song.getSongTitle(), song.getSongGenre(), song.getSongLink(),
        song.getSongImages(), song.getSongAlbum().getAlbumId());
    }

}
