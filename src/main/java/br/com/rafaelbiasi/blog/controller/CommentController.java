package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.facade.CommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import static br.com.rafaelbiasi.blog.controller.PostController.REDIRECT_POST;

@Slf4j
@Controller
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping("/add/post/{postCode}/")
    @PreAuthorize("isAuthenticated()")
    public String add(
            @Valid @ModelAttribute("comment") CommentData comment,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @PathVariable String postCode,
            Principal principal
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("comment", comment);
        } else {
            commentFacade.save(comment, postCode, principal);
        }
        return REDIRECT_POST + postCode + "/";
    }

    @PostMapping("/delete/{commentCode}/post/{postCode}/")
    @PreAuthorize("isAuthenticated()")
    public String delete(
            @PathVariable("commentCode") String commentCode,
            @PathVariable("postCode") String postCode
    ) {
        commentFacade.delete(commentCode);
        return REDIRECT_POST + postCode + "/";
    }
}