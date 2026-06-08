package com.ecotukar.controller;

import com.ecotukar.model.User;
import com.ecotukar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get list of all couriers
    @GetMapping("/couriers")
    public List<User> getCouriers() {
        return userRepository.findAll().stream()
                .filter(u -> "COURIER".equalsIgnoreCase(u.getRole()))
                .collect(Collectors.toList());
    }

    // Register a new courier
    @PostMapping("/couriers")
    public Map<String, String> createCourier(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String username = body.get("username");

        if (userRepository.findByUsername(username).isPresent()) {
            return Map.of("message", "Username sudah digunakan", "status", "error");
        }

        // Create new courier with default password "password"
        String encodedPassword = passwordEncoder.encode("password");
        User newCourier = new User(
                username,
                encodedPassword,
                name,
                username + "@ecotukar.id",
                "COURIER",
                "👷",
                "Hub Bandung",
                "Baru Bergabung",
                0
        );

        userRepository.save(newCourier);

        return Map.of("message", "Kurir berhasil ditambahkan", "status", "success");
    }

    // Assign schedule and courier for an existing pickup request
    @PostMapping("/pickups/{id}/schedule")
    public Map<String, String> assignSchedule(@PathVariable String id, @RequestBody Map<String, String> body) {
        String date = body.get("date");
        String time = body.get("time");
        String courier = body.get("courier");

        com.ecotukar.service.EcoTukarService service = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(
                ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                .getBean(com.ecotukar.service.EcoTukarService.class);
                
        service.assignSchedule(id, date, time, courier);

        return Map.of("message", "Jadwal penjemputan berhasil diatur", "status", "success");
    }
}
