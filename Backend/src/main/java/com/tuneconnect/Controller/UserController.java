package com.tuneconnect.Controller;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Entity.User;
import com.tuneconnect.Repository.UserRepository;
import com.tuneconnect.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final ImageService imageService;

    @PostMapping("/picture/{id}")
    public ResponseEntity<?> profilePicture(@PathVariable("id") Long id, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        try {
            User user = userRepository.findById(id).get();
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());

            if(bufferedImage == null) {
                return new ResponseEntity<String>("Invalid image", HttpStatus.BAD_REQUEST);
            }

            Image image = imageService.save(multipartFile);
            user.setProfilePicture(image);
            userRepository.save(user);
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
