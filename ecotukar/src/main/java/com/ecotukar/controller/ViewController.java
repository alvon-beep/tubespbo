package com.ecotukar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecotukar.service.EcoTukarService;
import com.ecotukar.model.User;
import java.security.Principal;

@Controller
public class ViewController {

    @Autowired
    private EcoTukarService ecoTukarService;

    @GetMapping("/")
    public String home(Principal principal) {
        return "home";
    }

    @GetMapping("/customer")
    public String customer(Model model, Principal principal) {
        if (principal != null) {
            User user = ecoTukarService.getUserByUsername(principal.getName());
            model.addAttribute("username", principal.getName());
            if(user != null) model.addAttribute("name", user.getName());
        }
        return "customer";
    }

    @GetMapping("/customer-ewallet")
    public String customerEwallet(Model model, Principal principal) {
        if (principal != null) {
            User user = ecoTukarService.getUserByUsername(principal.getName());
            model.addAttribute("username", principal.getName());
            if(user != null) model.addAttribute("name", user.getName());
        }
        return "customer-ewallet";
    }



    @GetMapping("/courier")
    public String courier(Model model, Principal principal) {
        if (principal != null) {
            User user = ecoTukarService.getUserByUsername(principal.getName());
            model.addAttribute("username", principal.getName());
            if(user != null) model.addAttribute("name", user.getName());
        }
        return "courier";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        if (principal != null) model.addAttribute("username", principal.getName());
        return "admin";
    }

    @GetMapping("/admin-jadwal")
    public String adminJadwal(Model model, Principal principal) {
        if (principal != null) model.addAttribute("username", principal.getName());
        return "admin-jadwal";
    }

    @GetMapping("/admin-konversi")
    public String adminKonversi(Model model, Principal principal) {
        if (principal != null) model.addAttribute("username", principal.getName());
        return "admin-konversi";
    }
}
