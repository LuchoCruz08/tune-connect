package com.tuneconnect.Service;
import com.tuneconnect.DTO.Request.AlbumRequestDTO;
import com.tuneconnect.DTO.Response.AlbumResponseDTO;
import com.tuneconnect.Entity.Album;
import com.tuneconnect.Entity.Artist;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Handler.AlbumException;
import com.tuneconnect.Repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;
    private final ImageService imageService;

    public List<AlbumResponseDTO> getAll() {
        List<Album> albumList = albumRepository.findAll();
        return albumList.stream()
                .map(AlbumResponseDTO::new).toList();
    }

    public AlbumResponseDTO getById(Long id) {
        Album album = albumRepository.findById(id).get();
        return new AlbumResponseDTO(album);
    }

    public AlbumResponseDTO create(String artistUsername, MultipartFile img, String albumTitle, String publicationDate) throws AlbumException {
        Album newAlbum = new Album();

        try {
            LocalDate publiDate = validateDate(publicationDate);
            Artist artist = artistService.getByUsername(artistUsername);
            Image imgAlbum = imageService.save(img);

            newAlbum.setAlbumDate(publiDate);
            newAlbum.setAlbumTitle(albumTitle);
            newAlbum.setAlbumArtist(artist);
            newAlbum.setAlbumImage(imgAlbum);

        } catch (IOException e) {
            e.printStackTrace();
            throw new AlbumException(e.getMessage());
        }

        return new AlbumResponseDTO(newAlbum);
    }

    public AlbumResponseDTO update(Long id, AlbumRequestDTO albumRequestDTO) throws AlbumException {
        Album albumUpdate = albumRepository.findById(id).get();
        LocalDate publicationDate = this.validateDate(albumRequestDTO.publicationDate());
        albumUpdate.setAlbumDate(publicationDate);

        albumRepository.save(albumUpdate);
        return new AlbumResponseDTO(albumUpdate);
    }

    public void delete(Long id) {
        albumRepository.deleteById(id);
    }

    public List<AlbumResponseDTO> getByArtist(String username) throws AlbumException {
        Optional<List<Album>> optionalAlbums = albumRepository.findByUsername(username);

        if(optionalAlbums.isPresent()) {
            List<Album> albumList = optionalAlbums.get();

            if(!albumList.isEmpty()) {

                return albumList.stream()
                        .map(AlbumResponseDTO::new)
                        .toList();
            } else {
                throw new AlbumException("The artist does not have any album", HttpStatus.OK);
            }

        } else {
            throw new AlbumException("The artist was not found", HttpStatus.BAD_REQUEST);
        }
    }

    public LocalDate validateDate(String date) throws AlbumException {
        try {
            return LocalDate.parse(date);
        } catch(DateTimeParseException e) {
            throw new AlbumException("The date format must be dd-MM-yyyy", HttpStatus.BAD_REQUEST);
        }
    }

}
