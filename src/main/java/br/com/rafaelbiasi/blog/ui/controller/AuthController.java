package br.com.rafaelbiasi.blog.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth/")
public class AuthController {

	private static final String AUTH_VIEW = "user/auth";

	@GetMapping("/")
	public String auth() {
		log.info("Entering the auth page.");
		return AUTH_VIEW;
	}

	@PostMapping("/error/")
	public String error(final Model model) {
		model.addAttribute("authError", true);
		return AUTH_VIEW;
	}

}
