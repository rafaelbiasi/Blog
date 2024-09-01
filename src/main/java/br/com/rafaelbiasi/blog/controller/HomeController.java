package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    public static final String REDIRECT_HOME = "redirect:/";

    private static final String HOME_VIEW = "home";

    private final PostFacade postFacade;

    @GetMapping({"/", "/page/{page}/"})
    public String home(@PathVariable(name = "page", required = false) Optional<Integer> pageNumberOpt,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {
        log.info(
                "Entering the home page. Parameters [{}={}, {}={}]",
                "Page number", pageNumberOpt.orElse(null),
                "Size", size
        );
        int page = pageNumberOpt.map(pn -> pn - 1).orElse(0);
        Pageable pageable = PageRequest.of(page, size);
        log.debug(
                "Fetching page posts. [{}={}]",
                "Pageable", pageable
        );
        Page<PostData> postsPage = postFacade.findAll(pageable);
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
        return HOME_VIEW;
    }
}
