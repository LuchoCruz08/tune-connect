package com.tuneconnect.DTO.Response;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Entity.Post;

public record PostResponseDTO(
        Long postId,
        String postContent,
        Image postImage

) {

    public PostResponseDTO(Post post) {
        this(post.getPostId(), post.getPostContent(), post.getPostImage(),
                new UserPostResponseDTO(
                        post.getUser().getUserId(),
                        post.getUser().getUsername(),
                        post.getUser().getProfilePicture()
                ));
    }

}
