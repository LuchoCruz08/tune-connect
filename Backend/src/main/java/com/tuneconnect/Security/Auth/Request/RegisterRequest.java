package com.tuneconnect.Security.Auth.Request;
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
public class RegisterRequest {

    @NotBlank(message = "Obligatory field.")
    @Email(message = "You must enter a valid email address.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "You must enter a valid email address.")
    String email;

    @NotBlank(message = "You must enter a password.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$", message = "La contrase\u00F1a no cumple con los requisitos, ingrese letras mayusculas y minusculas, numeros, un caracter especial y maximo 15")
    String password;
    String role;

    @NotBlank(message = "Obligatory field")
    @Size(min = 2, max = 30, message = "The name must contain between 2 and 30 characters.")
    String name;

    @NotBlank(message = "Obligatory field")
    @Size(min = 2, max = 30, message = "The last name must contain between 2 and 30 characters.")
    String lastname;

}
