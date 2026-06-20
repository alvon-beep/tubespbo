package com.ecotukar.controller;

import com.ecotukar.model.User;
import com.ecotukar.model.CustomerUser;
import com.ecotukar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String role,
            @RequestParam String username,
            @RequestParam String password) {
        if (userRepository.findByUsername(username) != null) {
            return "redirect:/register?error=Username sudah dipakai";
        }

        User user = new CustomerUser();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setAvatar("👨‍💼");

        String joined = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        user.setJoined(joined);
        user.setPoints(0);
        user.setEwalletBalance(0);

        userRepository.save(user);
        return "redirect:/login?success=Registrasi berhasil";
    }
}
