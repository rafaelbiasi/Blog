package br.com.rafaelbiasi.blog.ui.controller.admin;

import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.postNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwPostNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.util.ControllerUtil.expand;
import static java.util.function.Predicate.not;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/post/")
public class AdminPostController {

    public static final String REDIRECT_ADMIN_POST_LIST = "redirect:/admin/post/";
    public static final String REDIRECT_ADMIN_POST_EDIT = "redirect:/admin/post/edit/{code}/";

    private static final String LIST_VIEW = "admin/post/list";
    private static final String FORM_VIEW = "admin/post/form";

    private final PostFacade postFacade;

    @GetMapping({"/", "/page/{page}/"})
    public String list(@PathVariable(name = "page", required = false) Optional<Integer> pageNumberOpt,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {
        log.info(
                "Entering the home page. Parameters [{}={}, {}={}]",
                "Page number", pageNumberOpt.orElse(null),
                "Size", size
        );
        val page = pageNumberOpt.map(pn -> pn - 1).orElse(0);
        val pageable = PageRequest.of(page, size);
        log.debug(
                "Fetching page posts. [{}={}]",
                "Pageable", pageable
        );
        val postsPage = postFacade.findAll(pageable);
        log.info(
                "Fetched posts. [{}={}, {}={}, {}={}]",
                "Total Pages", postsPage.getTotalPages(),
                "Page number", postsPage.getNumber(),
                "Posts list size", postsPage.getContent().size()
        );
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("size", size);
        return LIST_VIEW;
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
                () -> throwPostNotFound(code)
        );
        return FORM_VIEW;
    }

    @GetMapping("/create/")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        log.info("Entering the new post page.");
        model.addAttribute("post", new PostData());
        return FORM_VIEW;
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
            return FORM_VIEW;
        }
        Optional.of(post)
                .map(PostData::getCode)
                .ifPresentOrElse(
                        (code) -> create(post, file, principal),
                        () -> save(post, file)
                );
        return expand(REDIRECT_ADMIN_POST_EDIT, Map.of("code", post.getCode()));

    }

    @PostMapping("/delete/{code}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable String code) {
        log.info(
                "Deleting the post. Parameters [{}={}]",
                "Code", code
        );
        if (!postFacade.delete(code)) {
            throw postNotFound(code);
        }
        return REDIRECT_ADMIN_POST_LIST;
    }

    private void create(PostData post, MultipartFile file, Principal principal) {
        log.info("Creating a new post.");
        post.setAuthor(AccountData.builder()
                .username(principal.getName())
                .build());
        save(post, file);
    }

    private void save(PostData post, MultipartFile multipartFile) {
        log.info(
                "Save the post. Parameters [{}={}]",
                "Code", post.getCode()
        );
        Optional.ofNullable(multipartFile)
                .filter(not(MultipartFile::isEmpty))
                .ifPresentOrElse(
                        (file) -> postFacade.save(post, file),
                        () -> postFacade.save(post)
                );
    }
}
