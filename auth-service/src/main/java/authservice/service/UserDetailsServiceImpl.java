package authservice.service;

import authservice.client.AccountServiceClient;
import authservice.dto.AccountClientResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountServiceClient userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountClientResponse user = userService.getByUsername(username).getBody();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        var roles = Stream.of(user.role())
                .map(SimpleGrantedAuthority::new)
                .toList();
        return  new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                roles
        );
    }
}
