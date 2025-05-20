package com.example.spring.webflux.security;

import com.example.spring.webflux.security.User;
import com.example.spring.webflux.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Default role can be set here if needed (e.g., "USER")
        if(user.getRole() == null) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
}
