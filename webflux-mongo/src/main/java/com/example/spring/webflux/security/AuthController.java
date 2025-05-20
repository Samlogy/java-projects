package com.example.spring.webflux.security;


import com.example.spring.webflux.security.User;
import com.example.spring.webflux.security.UserRepository;
import com.example.spring.webflux.security.JwtUtil;
import com.example.spring.webflux.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Registration endpoint
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Login endpoint: verifies credentials and returns a JWT token
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestParam String username, @RequestParam String password) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
                        return Mono.just(ResponseEntity.ok(token));
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
                    }
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
    }

    // Example of an endpoint accessible only to ADMIN role:
    @GetMapping("/admin/secure")
    public Mono<String> secureAdminEndpoint() {
        return Mono.just("Accessible only to ADMIN!");
    }

    // Example of an endpoint accessible to MODERATOR or ADMIN:
    @GetMapping("/moderator/secure")
    public Mono<String> secureModeratorEndpoint() {
        return Mono.just("Accessible to MODERATOR or ADMIN!");
    }
}
