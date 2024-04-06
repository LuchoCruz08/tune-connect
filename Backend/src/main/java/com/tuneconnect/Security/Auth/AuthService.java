package com.tuneconnect.Security.Auth;
import com.tuneconnect.Entity.Artist;
import com.tuneconnect.Entity.Enum.Role;
import com.tuneconnect.Repository.ArtistRepository;
import com.tuneconnect.Repository.UserRepository;
import com.tuneconnect.Security.Auth.Request.LoginRequest;
import com.tuneconnect.Security.Auth.Request.RegisterRequest;
import com.tuneconnect.Security.Auth.Response.AuthResponse;
import com.tuneconnect.Security.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userRepository.findByUsername(loginRequest.getEmail()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse signIn(RegisterRequest registerRequest) {
        Optional<Artist> optionalArtist = artistRepository.findByUsername(registerRequest.getEmail());

        if(optionalArtist.isPresent()) {
            throw new RuntimeException("There is already a registered user with that email");
        }

        Artist artist = new Artist();

        artist.setUsername(registerRequest.getEmail());
        artist.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        artist.setRole(Role.valueOf(registerRequest.getRole()));
        artist.setName(registerRequest.getName());
        artist.setLastname(registerRequest.getLastname());

        artistRepository.save(artist);
        return AuthResponse.builder().token(jwtService.getToken(artist)).build();
    }

}
