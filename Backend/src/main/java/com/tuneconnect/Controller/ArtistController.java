package com.tuneconnect.Controller;
import com.tuneconnect.DTO.Request.ArtistRequestDTO;
import com.tuneconnect.DTO.Response.ArtistResponseDTO;
import com.tuneconnect.Service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("getAll")
    public ResponseEntity<List<ArtistResponseDTO>> getAll() {
        List<ArtistResponseDTO> artistResponseDTOList = artistService.getAll();

        try {
            if(artistResponseDTOList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok(artistResponseDTOList);
        } catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable("id") Long id) {
        try {
            ArtistResponseDTO artistResponseDTO = artistService.getArtistById(id);
            return new ResponseEntity<>(artistResponseDTO, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        List<ArtistResponseDTO> artistResponseDTOList = artistService.getByName(name);

        try {
            if(name == null || name.length() < 3) {
                return ResponseEntity.status(HttpStatus.OK).body("The name must be more than three characters");
            }

            if(artistResponseDTOList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("There are no artists with that name");
            }

            return ResponseEntity.ok(artistResponseDTOList);
        } catch(ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByMusicalGenre")
    public ResponseEntity<?> getByMusicalGenre(@RequestParam String musicalGenre) {
        List<ArtistResponseDTO> artistResponseDTOList = artistService.getByMusicalGenre(musicalGenre);

        try {
            if(artistResponseDTOList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("There are no artists in that musical genre");
            }

            return ResponseEntity.ok(artistResponseDTOList);
        } catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ArtistRequestDTO artistRequestDTO) {
        try {
            ArtistResponseDTO artistResponseDTO = artistService.update(id, artistRequestDTO);
            return new ResponseEntity<>(artistResponseDTO, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        artistService.delete(id);
    }

}
