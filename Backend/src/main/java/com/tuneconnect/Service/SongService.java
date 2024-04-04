package com.tuneconnect.Service;
import com.tuneconnect.DTO.Response.SongResponseDTO;
import com.tuneconnect.Entity.*;
import com.tuneconnect.Handler.AlbumException;
import com.tuneconnect.Repository.AlbumRepository;
import com.tuneconnect.Repository.SongRepository;
import lombok.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ImageService imageService;

    @Value("${song.dir}")
    private String songDir;
    @Value("${ipServer}")
    private String serverAddress;

    public SongResponseDTO create(MultipartFile audio, MultipartFile img, String title, String genre, String albumId) throws AlbumException, IOException {
        String songLink = serverAddress + ":8081/music/" + audio.getOriginalFilename();
        Optional<Album> albumOptional = albumRepository.findById(Long.parseLong(albumId));

        if(albumOptional.isEmpty()) {
            throw new AlbumException("The album does not exist", HttpStatus.BAD_REQUEST);
        }

        if(!audio.isEmpty()) {
            String fileName = audio.getOriginalFilename();

            File uploadDirFile = new File(songDir);
            if(!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            Path filePath = Paths.get(songDir + fileName);
            FileCopyUtils.copy(audio.getBytes(), filePath.toFile());
        } else {
            throw new AlbumException("You haven't loaded a song", HttpStatus.BAD_REQUEST);
        }

        Image imgSong = imageService.save(img);
        Song newSong = new Song(title, genre, albumOptional.get(), imgSong.getImageUrl(), songLink);
        songRepository.save(newSong);

        return new SongResponseDTO(newSong);
    }

    public ResponseEntity<Resource> getSongByTitle(String songTitle) throws AlbumException {
        try {
            Path songPath = Paths.get(songDir, songTitle);
            Resource videoResource = new FileSystemResource(songPath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(videoResource.contentLength());
            headers.setContentDispositionFormData("attachment", songTitle);

            return ResponseEntity.ok().headers(headers).body(videoResource);
        } catch(Exception e) {
            throw new AlbumException("Song name error", HttpStatus.BAD_REQUEST);
        }
    }

    public List<SongResponseDTO> getAll() {

        return songRepository.findAll().stream()
                .map(song -> new SongResponseDTO(song.getSongId(), song.getSongTitle(),
                        song.getSongGenre(), song.getSongLink(), song.getSongImages(),
                        song.getSongLink(), song.getSongAlbum().getAlbumId()))
                .toList();

    }

    public List<SongResponseDTO> getSongsArtist(UserDetails userDetails) throws AlbumException {
        if(userDetails instanceof Artist) {
            User artist = (Artist) userDetails;
            Long artistId = artist.getUserId();

            Optional<List<Song>> optionalSongs = albumRepository.findSongByArtistId(artistId);

            if(optionalSongs.isEmpty()) {
                throw new AlbumException("Song list is empty", HttpStatus.BAD_REQUEST);
            }

            return optionalSongs.get().stream()
                    .map(song -> new SongResponseDTO(song.getSongId(), song.getSongTitle(),
                            song.getSongGenre(), song.getSongLink(), song.getSongImages(),
                            song.getSongLink(), song.getSongAlbum().getAlbumId()))
                    .toList();
        } else {
            throw new AlbumException("Error in the artist", HttpStatus.BAD_REQUEST);
        }
    }

}
