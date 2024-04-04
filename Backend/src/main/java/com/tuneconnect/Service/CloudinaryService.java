package com.tuneconnect.Service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    Cloudinary cloudinary;

    private Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryService() {
        // CREAR MI CUENTA DE CLOUDINARY Y RELLENAR CON MIS CREDENCIALES
        valuesMap.put("cloud_name", "dnckig4v9");
        valuesMap.put("api_key", "919948666226359");
        valuesMap.put("api_secret", "Fb8PQAMN3xLdS3yhQdSQjzCP9BU");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map<?, ?> upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map<?, ?> result = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "Photos/"));
        file.delete();
        return result;
    }

    public Map<?, ?> delete(String id) throws IOException {
        Map<?, ?> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return file;
    }

}
