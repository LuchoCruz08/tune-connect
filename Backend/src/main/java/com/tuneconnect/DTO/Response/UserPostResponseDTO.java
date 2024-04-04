package com.tuneconnect.DTO.Response;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Entity.User;

public record UserPostResponseDTO(
        Long id,
        String username,
        Image profilePicture
) {

    public UserPostResponseDTO(User user) {
        this(user.getUserId(), user.getUsername(), user.getProfilePicture());
    }

}
