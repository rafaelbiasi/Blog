package br.com.rafaelbiasi.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling auth page requests.
 * This controller serves the auth page to the user, enabling them to enter their credentials
 * for authentication. It uses a simple mapping to the auth view without any additional processing.
 */
@Slf4j
@Controller
public class AuthController {

    /**
     * Maps the "/auth" GET request to the auth view.
     * This method logs the access to the auth page and returns the name of the auth view,
     * which is typically resolved by a view resolver to the actual auth page.
     *
     * @return the name of the auth view to be rendered
     */
    @GetMapping("/auth")
    public String auth() {
        log.info("Entering the auth page.");
        return "auth";
    }

    @PostMapping("/auth/error")
    public String loginFailureHandler(Model model) {
        model.addAttribute("errorMessage", "A sua senha ou o seu login estão errados");
        return "auth";
    }

}
