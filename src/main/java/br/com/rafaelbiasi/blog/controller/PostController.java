package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.security.Principal;

import static br.com.rafaelbiasi.blog.controller.HomeController.REDIRECT_HOME;
import static br.com.rafaelbiasi.blog.exception.ResourceNotFoundExceptionFactory.postNoFound;
import static br.com.rafaelbiasi.blog.exception.ResourceNotFoundExceptionFactory.throwPostNoFound;
import static java.util.Optional.ofNullable;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post/")
public class PostController {

    public static final String REDIRECT_POST = "redirect:/post/";

    private static final String POST_FORM_VIEW = "post/post_form";
    private static final String POST_VIEW = "post/post";

    private final PostFacade postFacade;

    @GetMapping("/{code}/")
    public String post(@PathVariable String code, Model model, HttpServletRequest request) {
        log.info(
                "Entering the post page. Parameters [{}={}]",
                "Code", code
        );
        postFacade.findByCode(code).ifPresentOrElse(
                post -> addToModelAttribute(model, request, post),
                () -> throwPostNoFound(code)
        );
        return POST_VIEW;
    }

    private void addToModelAttribute(Model model, HttpServletRequest request, PostData post) {
        model.addAttribute("post", post);
        addFlashAttributeWhenError(model, request);
        addCommentAttribute(model);
    }

    @GetMapping("/edit/{code}/")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable String code, Model model) {
        log.info(
                "Entering the post edit page. Parameters [{}={}]",
                "Code", code
        );
        postFacade.findByCode(code).ifPresentOrElse(
                post -> model.addAttribute("post", post),
                () -> throwPostNoFound(code)
        );
        return POST_FORM_VIEW;
    }

    @GetMapping("/create/")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        log.info("Entering the new post page.");
        model.addAttribute("post", new PostData());
        return POST_FORM_VIEW;
    }

    @PostMapping("/save/")
    @PreAuthorize("isAuthenticated()")
    public String save(
            @Valid @ModelAttribute("post") PostData post,
            BindingResult result,
            Model model,
            @RequestParam("file") MultipartFile file,
            Principal principal) {
        log.info(
                "Saving the post. Parameters [{}={}, {}={}]",
                "Post", post,
                "File", file
        );
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return POST_FORM_VIEW;
        }
        PostData savedPostData = post.getCode() == null
                ? create(post, file, principal)
                : update(post, file);
        return REDIRECT_POST + savedPostData.getCode() + "/";

    }

    private PostData create(PostData post, MultipartFile file, Principal principal) {
        log.info("Creating a new post.");
        post.setAuthor(AccountData.builder().username(principal.getName()).build());
        return file == null || file.isEmpty()
                ? postFacade.save(post)
                : postFacade.save(post, file);
    }

    private PostData update(PostData post, MultipartFile file) {
        log.info(
                "Updating the post. [{}={}]",
                "Code", post.getCode()
        );
        return file == null || file.isEmpty()
                ? postFacade.save(post)
                : postFacade.save(post, file);
    }

    @GetMapping("/delete/{code}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable String code) {
        log.info(
                "Deleting the post. Parameters [{}={}]",
                "Code", code
        );
        if (!postFacade.delete(code)) {
            throw postNoFound(code);
        }
        return REDIRECT_HOME;
    }

    private void addFlashAttributeWhenError(Model model, HttpServletRequest request) {
        ofNullable(RequestContextUtils.getInputFlashMap(request)).ifPresent(model::addAllAttributes);
    }

    private void addCommentAttribute(Model model) {
        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new CommentData());
        }
    }
}
