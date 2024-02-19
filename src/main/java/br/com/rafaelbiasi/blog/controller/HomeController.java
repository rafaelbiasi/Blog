package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.util.LogId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Controller for handling the home page requests.
 * This controller uses the {@link PostFacade} to fetch paginated blog posts to display on the home page.
 * It supports dynamic pagination through request parameters and path variables.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostFacade postFacade;

    @GetMapping({"/", "/page/{page}"})
    public String home(@PathVariable(name = "page", required = false) Optional<Integer> pageNumberOpt,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {
        String logId = LogId.logId();
        log.info("#{}={}. Entering the home page. Parameters [{}={}, {}={}]",
                "LogID", logId,
                "Page number", pageNumberOpt.orElse(null),
                "Size", size
        );
        int page = pageNumberOpt.map(pn -> pn - 1).orElse(0);
        try {
            Pageable pageable = PageRequest.of(page, size);
            log.debug("#{}={}. Fetching page posts. [{}={}]",
                    "LogID", logId,
                    "Pageable", pageable);
            Page<PostData> postsPage = postFacade.getAll(pageable);
            log.info("#{}={}. Fetched posts. [{}={}, {}={}, {}={}]",
                    "LogID", logId,
                    "Total Pages", postsPage.getTotalPages(),
                    "Page number", postsPage.getNumber(),
                    "Posts list size", postsPage.getContent().size()
            );
            model.addAttribute("posts", postsPage.getContent());
            model.addAttribute("currentPage", postsPage.getNumber());
            model.addAttribute("totalPages", postsPage.getTotalPages());
            model.addAttribute("size", size);
        } catch (Exception e) {
            log.error("#{}={}. Exception encountered while loading posts",
                    "LogID", logId, e);
            return "error403";
        }
        return "home";
    }
}
