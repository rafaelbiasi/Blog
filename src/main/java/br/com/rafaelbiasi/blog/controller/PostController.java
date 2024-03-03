package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.util.LogId;
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
import java.util.Map;
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
        String logId = LogId.logId();
        log.info("#{}={}. Entering the post page. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        log.info("#{}={}. Fetching post. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        Optional<PostData> post = postFacade.findByCode(code);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            Optional<Map<String, ?>> flashAttributes = ofNullable(RequestContextUtils.getInputFlashMap(request));
            if (flashAttributes.isPresent()) {
                model.addAllAttributes(flashAttributes.get());
            }
            if (!model.containsAttribute("comment")) {
                model.addAttribute("comment", new CommentData());
            }
            return "post";
        } else {
            return "error404";
        }
    }

    @GetMapping("/{code}/edit")
    @PreAuthorize("isAuthenticated()")
    public String update(@PathVariable String code, Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the post edit page. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        log.info("#{}={}. Fetching post. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        Optional<PostData> post = postFacade.findByCode(code);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "post_edit";
        } else {
            return "error404";
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
        String logId = LogId.logId();
        log.info("#{}={}. Updating the post. Parameters [{}={}, {}={}]",
                "LogID", logId,
                "Code", code,
                "File", file
        );
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return "post_edit";
        }
        try {
            ofNullable(file).ifPresentOrElse(
                    multipartFile -> postFacade.save(post, multipartFile),
                    () -> postFacade.save(post)
            );
            return "redirect:/post/" + code;
        } catch (Exception e) {
            log.error("Error updating post. [{}={}]", "Code", code, e);
            return "error500";
        }
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the new post page.",
                "LogID", logId
        );
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
        String logId = LogId.logId();
        log.info("#{}={}. Saving the new post. Parameters [{}={}, {}={}, {}={}]",
                "LogID", logId,
                "Post", post,
                "File", file,
                "Principal", principal
        );
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            return "post_new";
        }
        try {
            log.info("#{}={}. Fetching principal. Parameters [{}={}]",
                    "LogID", logId,
                    "Principal", principal
            );
            ofNullable(file).ifPresentOrElse(
                    multipartFile -> postFacade.save(post, multipartFile, principal),
                    () -> postFacade.save(post, principal)
            );
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error creating post. [{}={}]", "Post", post, e);
            return "error500";
        }
    }

    @GetMapping("/{code}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable String code) {
        String logId = LogId.logId();
        log.info("#{}={}. Deleting the post. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        postFacade.delete(code);
        return "redirect:/";
    }

}
