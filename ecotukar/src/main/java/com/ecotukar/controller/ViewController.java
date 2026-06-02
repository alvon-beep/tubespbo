package com.ecotukar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/customer")
    public String customer() {
        return "customer";
    }

    @GetMapping("/courier")
    public String courier() {
        return "courier";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
