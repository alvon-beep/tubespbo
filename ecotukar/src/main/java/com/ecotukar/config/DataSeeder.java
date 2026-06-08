package com.ecotukar.config;

import com.ecotukar.model.User;
import com.ecotukar.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            String encodedPassword = passwordEncoder.encode("password");
            
            userRepository.save(new User("customer1", encodedPassword, "Customer Satu", "customer1@ecotukar.id", "CUSTOMER", "", "", "", 0));
            userRepository.save(new User("kurir1", encodedPassword, "Kurir Satu", "kurir1@ecotukar.id", "COURIER", "", "", "", 0));
            userRepository.save(new User("admin", encodedPassword, "Admin", "admin@ecotukar.id", "ADMIN", "", "", "", 0));
        }
    }
}
