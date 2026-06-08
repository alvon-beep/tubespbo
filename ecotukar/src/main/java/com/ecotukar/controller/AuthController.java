package com.ecotukar.controller;

import com.ecotukar.model.User;
import com.ecotukar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String address,
                               @RequestParam String password) {
                               
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register?error=Username sudah digunakan";
        }

        String joinedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("id", "ID")));

        User newUser = new User(
                username,
                passwordEncoder.encode(password),
                name,
                email,
                "CUSTOMER",
                "👩‍🦰", // default avatar
                address,
                joinedDate,
                0
        );

        userRepository.save(newUser);
        return "redirect:/login?registered=true";
    }
}
