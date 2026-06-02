package com.ecotukar.user;

import com.ecotukar.user.dto.LoginRequest;
import com.ecotukar.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController — REST API endpoint untuk manajemen user.
 *
 * @RestController    → kombinasi @Controller + @ResponseBody (return JSON otomatis)
 * @RequestMapping    → base URL untuk semua endpoint di class ini
 * @CrossOrigin("*")  → izinkan request dari semua origin (HTML frontend)
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // ===== AUTH =====

    // POST /api/auth/register
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            UserEntity user = userService.register(req);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /api/auth/login
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            UserEntity user = userService.login(req);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===== USER =====

    // GET /api/users — ambil semua user (admin)
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{id} — get profil user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/users/{id} — update profil user
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserEntity updatedData) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, updatedData));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
