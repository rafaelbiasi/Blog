package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.application.facade.AccountFacade;
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

    private final AccountFacade accountFacade;

    @GetMapping("/")
    public String register(final Model model) {
        log.info("Entering register page.");
        model.addAttribute("account", new AccountData());
        return REGISTER_VIEW;
    }

    @PostMapping("/")
    public String register(
            final @Valid @ModelAttribute("account") AccountData account,
            final BindingResult result,
            final Model model
    ) {
        log.info("Saving new user. Parameters: [{}={}]", "Account", account);
        checkError(result, account);
        if (result.hasErrors()) {
            model.addAttribute("account", account);
            return REGISTER_VIEW;
        }
        accountFacade.registerUser(account);
        return REDIRECT_HOME;
    }

    private void checkError(
            final BindingResult result,
            final AccountData account
    ) {
        val registrationResponse = accountFacade.checkEmailAndUsernameExists(account);
        if (registrationResponse.success()) {
            return;
        }
        if (registrationResponse.usernameExists()) {
            result.addError(new FieldError(
                    "account",
                    "username",
                    "Another user has already registered this username."));
        }
        if (registrationResponse.emailExists()) {
            result.addError(new FieldError(
                    "account",
                    "email",
                    "Another user has already registered this e-mail."
            ));
        }
    }
}
