package com.tuneconnect.DTO.Response;
import com.tuneconnect.Entity.Album;
import com.tuneconnect.Entity.Event;
import lombok.Data;
import java.util.List;

@Data
public class ArtistResponseDTO {

    private Long artistId;

    private String artistName;

    private String artistDescription;

    private List<Album> artistAlbums;

    private List<Event> artistEvents;

    private String musicalGenre;

}
