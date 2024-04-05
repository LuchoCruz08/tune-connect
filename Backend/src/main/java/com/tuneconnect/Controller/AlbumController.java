package com.tuneconnect.Controller;
import com.tuneconnect.DTO.Request.AlbumRequestDTO;
import com.tuneconnect.DTO.Response.AlbumResponseDTO;
import com.tuneconnect.Handler.AlbumException;
import com.tuneconnect.Service.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<AlbumResponseDTO> albumResponseDTOList = albumService.getAll();
        return new ResponseEntity<>(albumResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(@PathVariable("id") Long id) {
        AlbumResponseDTO albumResponseDTO = albumService.getById(id);
        return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> searchByArtist(@AuthenticationPrincipal UserDetails userDetails) {
        String artistUsername = userDetails.getUsername();
        List<AlbumResponseDTO> albumResponseDTOList = null;

        try {
            albumResponseDTOList = albumService.getByArtist(artistUsername);
        } catch(AlbumException e) {
            return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
        }

        return new ResponseEntity<>(albumResponseDTOList, HttpStatus.OK);
    }

    @Secured("ARTIST")
    @PostMapping("")
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("img")MultipartFile img, @RequestParam("title") String title,
                                    @RequestParam("genre") String genre, @RequestParam("publicationDate") String publicationDate) throws IOException {

        String artistUsername = userDetails.getUsername();

        try {
            albumService.create(artistUsername, img, title, genre, publicationDate);
            return ResponseEntity.status(HttpStatus.CREATED).body("Album created");
        } catch(AlbumException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody AlbumRequestDTO albumRequestDTO) {
        try {
            AlbumResponseDTO albumResponseDTO = albumService.update(id, albumRequestDTO);
            return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
        } catch(AlbumException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        albumService.delete(id);
    }


}
