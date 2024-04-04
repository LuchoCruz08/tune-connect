package com.tuneconnect.DTO.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlbumRequestDTO(
        @NotBlank(message = "The field cannot be empty")
        @Size(max = 30, message = "The 'musical genre' field must have a maximum of 30 characters")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+$", message = "Only text is allowed in the 'music genre' field")
        String musicalGenre,

        @NotBlank(message = "The date field must not be empty")
        String publicationDate,

        @NotBlank(message = "The field cannot be empty")
        @Size(max = 30, message = "The 'title' field must have a maximum of 30 characters")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$", message = "Only text is allowed in the 'title' field")
        String title
        ) {

}
