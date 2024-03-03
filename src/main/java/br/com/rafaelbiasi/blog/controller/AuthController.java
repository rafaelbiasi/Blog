package br.com.rafaelbiasi.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthController {

    @GetMapping("/auth")
    public String auth() {
        log.info("Entering the auth page.");
        return "auth";
    }

    @PostMapping("/auth/error")
    public String error(Model model) {
        model.addAttribute("authError", true);
        return "auth";
    }

}
