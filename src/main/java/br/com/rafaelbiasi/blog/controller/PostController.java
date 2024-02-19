package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import br.com.rafaelbiasi.blog.facade.FileFacade;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.util.LogId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Controller for managing blog posts, including displaying, creating, editing, and deleting posts.
 * This controller interacts with the PostFacade, AccountFacade, and FileFacade to perform its operations,
 * and employs Spring Security annotations to enforce authorization constraints on certain actions.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostFacade postFacade;
    private final AccountFacade accountFacade;
    private final FileFacade fileFacade;

    /**
     * Displays a single blog post identified by its code.
     *
     * @param code  the unique code of the post to display
     * @param model the Spring MVC {@link Model} for passing data to the view
     * @return the name of the view template to render the post
     */
    @GetMapping("/posts/{code}")
    public String post(@PathVariable String code, Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the post page. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        return post(logId, code, "post", model);
    }

    /**
     * Handles the submission of updated post, including an optional image file.
     *
     * @param code the unique code of the post being updated
     * @param post the updated post
     * @param file the image file associated with the post, if any
     * @return a redirect URL to the updated post's detail page
     */
    @PostMapping("/posts/{code}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable String code, PostData post, @RequestParam("file") MultipartFile file) {
        String logId = LogId.logId();
        log.info("#{}={}. Updating the post. Parameters [{}={}, {}={}]",
                "LogID", logId,
                "Code", code,
                "File name", file.getName()
        );
        try {
            processAndSavePost(logId, post, file);
            return "redirect:/posts/" + code;
        } catch (Exception e) {
            log.error("Error updating post. [{}={}]", "Code", code, e);
            return "error500";
        }
    }

    /**
     * Displays the form for creating a new blog post.
     *
     * @param model the Spring MVC {@link Model} for passing data to the view
     * @return the name of the view template for creating a new post
     */
    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the new post page.",
                "LogID", logId
        );
        model.addAttribute("post", new PostData());
        return "post_new";
    }

    /**
     * Handles the submission of a new blog post, including saving the associated image file, if any.
     *
     * @param post      the new post
     * @param file      the image file associated with the new post, if any
     * @param principal the {@link Principal} object representing the currently authenticated user
     * @return a redirect URL to the blog's home page
     */
    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(@ModelAttribute PostData post, @RequestParam("file") MultipartFile file, Principal principal) {
        String logId = LogId.logId();
        log.info("#{}={}. Saving the new post. Parameters [{}={}, {}={}, {}={}]",
                "LogID", logId,
                "Post", post,
                "File name", file.getName(),
                "Principal", principal
        );
        try {
            AccountData account = getAuthenticatedAccount(logId, principal);
            log.debug("#{}={}. Principal fetched. Parameters [{}={}, {}={}]",
                    "LogID", logId,
                    "Account", account,
                    "Principal", principal
            );
            post.setAccount(account);
            processAndSavePost(logId, post, file);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error creating post. [{}={}]", "Post", post, e);
            return "error500";
        }
    }

    /**
     * Displays the form for editing an existing blog post identified by its code.
     *
     * @param code  the unique code of the post to edit
     * @param model the Spring MVC {@link Model} for passing data to the view
     * @return the name of the view template for editing the post
     */
    @GetMapping("/posts/{code}/edit")
    @PreAuthorize("isAuthenticated()")
    public String editPost(@PathVariable String code, Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the post edit page. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        return post(logId, code, "post_edit", model);
    }

    /**
     * Deletes a blog post identified by its code.
     *
     * @param code the unique code of the post to delete
     * @return a redirect URL to the blog's home page
     */
    @GetMapping("/posts/{code}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePost(@PathVariable String code) {
        String logId = LogId.logId();
        log.info("#{}={}. Deleting the post. Parameters [{}={}]",
                "LogID", logId,
                "Code", code
        );
        postFacade.getByCode(code).ifPresent(postFacade::delete);
        return "redirect:/";
    }

    private String post(String logId, String code, String view, Model model) {
        log.info("#{}={}. Fetching post. Parameters [{}={}, {}={}]",
                "LogID", logId,
                "Code", code,
                "View", view
        );
        return postFacade.getByCode(code)
                .map(post -> {
                    model.addAttribute("post", post);
                    return view;
                })
                .orElse("404");
    }

    private AccountData getAuthenticatedAccount(String logId, Principal principal) {
        log.info("#{}={}. Fetching principal. Parameters [{}={}]",
                "LogID", logId,
                "Principal", principal
        );
        String authUsername = principal != null ? principal.getName() : "anonymousUser";
        return accountFacade.findOneByEmail(authUsername)
                .orElseThrow(() -> {
                    log.error("Account not found for username: {}", authUsername);
                    return new IllegalArgumentException("Account not found");
                });
    }

    private void processAndSavePost(String logId, PostData post, MultipartFile file) {
        log.debug("\"#{}={}. Processing and saving new post. Parameters [{}={}, {}={}]",
                "LogID", logId,
                "Post", post,
                "File name", file.getName());
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            try {
                fileFacade.save(file);
                post.setImageFilePath(originalFilename);
            } catch (Exception e) {
                log.error("Error processing file: {}", originalFilename, e);
                throw e;
            }
        }
        postFacade.save(post);
    }
}
