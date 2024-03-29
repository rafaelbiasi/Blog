package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import br.com.rafaelbiasi.blog.util.LogId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AccountFacade accountFacade;

    @GetMapping("/register")
    public String register(Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering register page.", "LogID", logId);
        model.addAttribute("account", new AccountData());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") AccountData account, BindingResult result, Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Saving new user. Parameters: [{}={}]", "LogID", logId, "Account", account);
        try {
            checkError(result, account);
            if (result.hasErrors()) {
                model.addAttribute("account", account);
                return "register";
            }
            accountFacade.attemptUserRegistration(account);
        } catch (Exception e) {
            log.error("#{}={}. Registration failed for user", "LogID", logId, e);
            return "error403";
        }
        return "redirect:/";
    }

    private void checkError(BindingResult result, AccountData account) {
        RegistrationResponseData registrationResponse = accountFacade.checkEmailAndUsernameExists(account);
        if (registrationResponse.fail()) {
            if (registrationResponse.usernameExists()) {
                result.addError(new FieldError("account", "username", "Username is already taken"));
            }
            if (registrationResponse.emailExists()) {
                result.addError(new FieldError("account", "email", "E-mail is already taken"));
            }
        }
    }
}
