package com.tuneconnect.Controller;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Service.CloudinaryService;
import com.tuneconnect.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAll() {
        List<Image> imageList = imageService.list();
        return new ResponseEntity<List<Image>>(imageList, HttpStatus.OK);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {
        Image image = imageService.getImage(id).get();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());

        if(bufferedImage == null) {
            return new ResponseEntity<String>("Invalid image", HttpStatus.BAD_REQUEST);
        }

        Image image = imageService.save(multipartFile);
        return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException {
        if(!imageService.exists(id)) {
            return new ResponseEntity<String>("The image does not exist", HttpStatus.NOT_FOUND);
        }

        Image image = imageService.getImage(id).get();
        cloudinaryService.delete(image.getCloudinaryId());
        imageService.delete(id);
        return new ResponseEntity<>("Deleted image", HttpStatus.OK);
    }

}
