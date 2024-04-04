package com.tuneconnect.DTO.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequestDTO {

    String name;
    String lastname;

    @NotBlank (message = "Obligatory field")
    @Email(message = "You must enter a valid email address")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "You must enter a valid email address")
    String email;
    String role;
    @NotBlank (message = "Obligatory field")
    @Size(min = 2, max = 30, message = "The artist name must contain between 2 and 30 characters.")
    String artistName;
    @NotBlank (message = "Obligatory field")
    @Size(min = 10, max = 100, message = "The description must contain between 10 and 100 characters.")
    String description;

    String musicalGenre;

}
