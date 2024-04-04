package com.tuneconnect.Mapper;
import com.tuneconnect.DTO.Response.ArtistResponseDTO;
import com.tuneconnect.Entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "profilePicture", source = "profilePicture.imageUrl")
    ArtistResponseDTO ArtistToArtistDto(Artist artist);

    @Mapping(target = "profilePicture", source = "profilePicture.imageUrl")
    List<ArtistResponseDTO> toDtoList(List<Artist> artistList);

}
