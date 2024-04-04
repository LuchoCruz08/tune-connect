package com.tuneconnect.Service;
import com.tuneconnect.DTO.Request.ArtistRequestDTO;
import com.tuneconnect.DTO.Response.ArtistResponseDTO;
import com.tuneconnect.Entity.Artist;
import com.tuneconnect.Entity.Enum.Role;
import com.tuneconnect.Handler.UserNotExistException;
import com.tuneconnect.Mapper.ArtistMapper;
import com.tuneconnect.Repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public List<ArtistResponseDTO> getAll() {
        List<Artist> artistList = artistRepository.findAll();
        return artistMapper.toDtoList(artistList);
    }

    public ArtistResponseDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id).get();
        return artistMapper.ArtistToArtistDto(artist);
    }

    public List<ArtistResponseDTO> getByName(String name) {
        List<Artist> artistList = artistRepository.findByName(name);
        return artistMapper.toDtoList(artistList);
    }

    public List<ArtistResponseDTO> getByMusicalGenre(String musicalGenre) {
        List<Artist> artistList = artistRepository.findByMusicalGenre(musicalGenre);
        return artistMapper.toDtoList(artistList);
    }

    public ArtistResponseDTO update(Long id, ArtistRequestDTO artistRequestDTO) {
        Artist artistUpdate = artistRepository.findById(id).get();

        artistUpdate.setName(artistRequestDTO.getName());
        artistUpdate.setLastname(artistRequestDTO.getLastname());
        artistUpdate.setUsername(artistRequestDTO.getEmail());
        artistUpdate.setRole(Role.valueOf(artistRequestDTO.getRole()));
        artistUpdate.setArtistName(artistRequestDTO.getArtistName());
        artistUpdate.setArtistDescription(artistRequestDTO.getDescription());
        artistUpdate.setMusicalGenre(artistRequestDTO.getMusicalGenre());

        return artistMapper.ArtistToArtistDto(artistUpdate);
    }

    public void delete(Long id) {
        Optional<Artist> artistOptional = artistRepository.findById(id);

        if(artistOptional.isEmpty()) {
            throw new UserNotExistException();
        }

        artistRepository.deleteById(id);
    }

    public Artist getByUsername(String username) {
        Optional<Artist> artistOptional = artistRepository.findByUsername(username);
        return artistOptional.get();
    }

}
