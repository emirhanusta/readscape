package authservice.service;

import authservice.dto.LoginRequest;
import authservice.dto.RegisterRequest;
import authservice.dto.TokenResponse;
import authservice.exception.UserAlreadyExistException;
import authservice.dto.UserResponse;
import authservice.model.Role;
import authservice.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public UserResponse register(RegisterRequest request) {
        log.info("Registering user: {}", request.username());
        if (userService.existsByUsername(request.username())) {
            throw new UserAlreadyExistException("Username already exists");
        }
        return userService.saveUser(User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role()))
                .build());
    }

    public TokenResponse login(LoginRequest request) {
        UserResponse userResponse = userService.findByUsername(request.username());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            return TokenResponse.builder()
                    .user(userResponse)
                    .token(tokenService.generateToken(request.username()))
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }
}
