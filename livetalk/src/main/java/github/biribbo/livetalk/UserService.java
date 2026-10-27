package github.biribbo.livetalk;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @NotNull
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));
    }

    public User registerUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Użytkownik o tej nazwie już istnieje");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        return userRepository.save(user);
    }
}
