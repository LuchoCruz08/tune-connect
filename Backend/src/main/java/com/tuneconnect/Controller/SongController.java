package com.tuneconnect.Controller;
import com.tuneconnect.DTO.Response.SongResponseDTO;
import com.tuneconnect.Handler.AlbumException;
import com.tuneconnect.Service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> getAll() {
        return ResponseEntity.ok(songService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam("audio")MultipartFile audio, @RequestParam("img") MultipartFile img,
                                    @RequestParam("title") String title, @RequestParam("musicalGenre") String musicalGenre,
                                    @RequestParam("publicationDate") String publicationDate, @RequestParam("albumId") String albumId) {

        try {
            return ResponseEntity.ok(songService.create(audio, img, title, musicalGenre, publicationDate, albumId));
        } catch(AlbumException e) {
            return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{songName}")
    public ResponseEntity<?> getSongByName(@PathVariable String songName) {
        try {
            return songService.getSongByTitle(songName);
        } catch(AlbumException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{songsByArtist}")
    public ResponseEntity<?> getSongsByArtist(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            return new ResponseEntity<>(songService.getSongsArtist(userDetails), HttpStatus.OK);
        } catch(AlbumException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
