package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register/")
public class RegisterController {

    public static final String REGISTER_VIEW = "user/register";

    private static final String REDIRECT_HOME = "redirect:/";

    private final UserFacade userFacade;

    @GetMapping("/")
    public String register(final Model model) {
        log.info("Entering register page.");
        model.addAttribute("user", new UserData());
        return REGISTER_VIEW;
    }

    @PostMapping("/")
    public String register(
            final @Valid @ModelAttribute("user") UserData userData,
            final BindingResult result,
            final Model model
    ) {
        log.info("Saving new user. Parameters: [{}={}]", "UserData", userData);
        checkError(result, userData);
        if (result.hasErrors()) {
            model.addAttribute("user", userData);
            return REGISTER_VIEW;
        }
        userFacade.registerUser(userData);
        return REDIRECT_HOME;
    }

    private void checkError(
            final BindingResult result,
            final UserData userData
    ) {
        val registrationResponse = userFacade.checkEmailAndUsernameExists(userData);
        if (registrationResponse.success()) {
            return;
        }
        if (registrationResponse.usernameExists()) {
            result.addError(new FieldError(
                    "user",
                    "username",
                    "Another user has already registered this username."));
        }
        if (registrationResponse.emailExists()) {
            result.addError(new FieldError(
                    "user",
                    "email",
                    "Another user has already registered this e-mail."
            ));
        }
    }
}
