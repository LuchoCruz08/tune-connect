package com.tuneconnect.DTO.Request;
import com.tuneconnect.Entity.Post;
import org.springframework.web.multipart.MultipartFile;

public record PostRequestDTO(
        String message,
        MultipartFile image
) {

    public PostRequestDTO(Post post) {
        this(post.getPostContent(), (MultipartFile) post.getPostImage());
    }

}
