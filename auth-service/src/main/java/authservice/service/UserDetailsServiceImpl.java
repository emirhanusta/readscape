package authservice.service;

import authservice.model.User;
import authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        var roles = Stream.of(user.get().getRole())
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
        return  new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                roles
        );
    }
}
