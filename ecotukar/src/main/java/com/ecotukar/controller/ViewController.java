package com.ecotukar.controller;

import com.ecotukar.model.Akun;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        // If already logged in, redirect to respective dashboard
        Akun user = (Akun) session.getAttribute("user");
        if (user != null) {
            if ("CUSTOMER".equals(user.getRole())) return "redirect:/customer";
            if ("TUKANG".equals(user.getRole())) return "redirect:/courier";
            if ("ADMIN".equals(user.getRole())) return "redirect:/admin";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear session credentials
        return "redirect:/login";
    }

    @GetMapping("/customer")
    public String customer(HttpSession session) {
        Akun user = (Akun) session.getAttribute("user");
        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        return "customer";
    }

    @GetMapping("/courier")
    public String courier(HttpSession session) {
        Akun user = (Akun) session.getAttribute("user");
        if (user == null || !"TUKANG".equals(user.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        return "courier";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        Akun user = (Akun) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        return "admin";
    }
}
