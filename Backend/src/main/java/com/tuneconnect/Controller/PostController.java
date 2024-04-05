package com.tuneconnect.Controller;
import com.tuneconnect.DTO.Request.PostRequestDTO;
import com.tuneconnect.DTO.Response.PostResponseDTO;
import com.tuneconnect.Handler.UserNotExistException;
import com.tuneconnect.Service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAll() {
        List<PostResponseDTO> postResponseDTOList = postService.getAllPost();
        return ResponseEntity.ok(postResponseDTOList);
    }

    @GetMapping("/{postByUser}")
    public ResponseEntity<?> getPostByUser(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = userDetails.getUsername();
            List<PostResponseDTO> postResponseDTOList = postService.getPostByUsername(username);
            return ResponseEntity.ok(postResponseDTOList);
        } catch(EntityNotFoundException | UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart(value = "image", required = false)MultipartFile image,
                                    @RequestPart(value = "message") String message, @AuthenticationPrincipal UserDetails userDetails) {

        try {
            String username = userDetails.getUsername();
            PostRequestDTO postRequestDTO = new PostRequestDTO(message, image);
            PostResponseDTO postResponseDTO = postService.create(postRequestDTO, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
        } catch(UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing image");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestParam(value = "image", required = false) MultipartFile image,
                                    @RequestParam(value = "message") String message, @AuthenticationPrincipal UserDetails userDetails) {

        try {
            String username = userDetails.getUsername();
            PostRequestDTO postRequestDTO = new PostRequestDTO(message, image);
            PostResponseDTO postResponseDTO = postService.update(id, postRequestDTO, username);
            return ResponseEntity.ok(postResponseDTO);
        } catch(UserNotExistException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        postService.delete(id, username);
    }

}
