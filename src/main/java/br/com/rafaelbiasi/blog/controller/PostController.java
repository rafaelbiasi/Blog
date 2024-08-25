package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.exception.ResourceNotFoundException;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.security.Principal;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@RequestMapping("post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostFacade postFacade;

    @GetMapping("/{code}")
    public String post(@PathVariable String code, Model model, HttpServletRequest request) {
        log.info("Entering the post page. Parameters [{}={}]",
                "Code", code
        );
        Optional<PostData> post = postFacade.findByCode(code);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            addFlashAttributeWhenError(model, request);
            addCommentAttribute(model);
            return "post";
        } else {
            throw new ResourceNotFoundException("Post not found for [code=" + code + "]");
        }
    }

    @GetMapping("/{code}/edit")
    @PreAuthorize("isAuthenticated()")
    public String update(@PathVariable String code, Model model) {
        log.info("Entering the post edit page. Parameters [{}={}]",
                "Code", code
        );
        Optional<PostData> post = postFacade.findByCode(code);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "post_edit";
        } else {
            throw new ResourceNotFoundException("Post not found for code: " + code);
        }
    }

    @PostMapping("/{code}")
    @PreAuthorize("isAuthenticated()")
    public String update(
            @Valid @ModelAttribute("post") PostData post,
            BindingResult result,
            Model model,
            @PathVariable String code,
            @RequestParam("file") MultipartFile file) {
        log.info("Updating the post. Parameters [{}={}, {}={}]",
                "Code", code,
                "File", file
        );
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return "post_edit";
        }
        ofNullable(file).ifPresentOrElse(
                multipartFile -> save(post, multipartFile),
                () -> postFacade.save(post)
        );
        return "redirect:/post/" + code;
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        log.info("Entering the new post page.");
        model.addAttribute("post", new PostData());
        return "post_new";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(
            @Valid @ModelAttribute("post") PostData post,
            BindingResult result,
            Model model,
            @RequestParam("file") MultipartFile file,
            Principal principal) {
        log.info("Saving the new post. Parameters [{}={}, {}={}, {}={}]",
                "Post", post,
                "File", file,
                "Principal", principal
        );
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return "post_new";
        }
        log.info("Fetching principal. Parameters [{}={}]",
                "Principal", principal
        );
        ofNullable(file).ifPresentOrElse(
                multipartFile -> save(post, principal, multipartFile),
                () -> postFacade.save(post, principal)
        );
        return "redirect:/";
    }

    @GetMapping("/{code}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable String code) {
        log.info("Deleting the post. Parameters [{}={}]",
                "Code", code
        );
        if (postFacade.delete(code)) {
            return "redirect:/";
        }
        throw new ResourceNotFoundException("Post not found for [code=" + code + "]");
    }

    private void addFlashAttributeWhenError(Model model, HttpServletRequest request) {
        ofNullable(RequestContextUtils.getInputFlashMap(request)).ifPresent(model::addAllAttributes);
    }

    private void addCommentAttribute(Model model) {
        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new CommentData());
        }
    }

    @SneakyThrows
    private void save(PostData post, MultipartFile multipartFile) {
        postFacade.save(post, multipartFile);
    }

    @SneakyThrows
    private void save(PostData post, Principal principal, MultipartFile multipartFile) {
        postFacade.save(post, multipartFile, principal);
    }

}
