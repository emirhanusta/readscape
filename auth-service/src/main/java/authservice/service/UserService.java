package authservice.service;

import authservice.dto.UserResponse;
import authservice.exception.UserNotFoundException;
import authservice.model.User;
import authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    protected UserResponse saveUser(User user) {
        return UserResponse.from(userRepository.save(user));
    }

    protected UserResponse findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserResponse::from)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
