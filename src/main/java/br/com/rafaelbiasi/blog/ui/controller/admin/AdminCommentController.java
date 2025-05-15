package br.com.rafaelbiasi.blog.ui.controller.admin;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.facade.CommentFacade;
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

import java.util.Map;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.commentNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwCommentNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.util.ControllerUtil.expand;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comment/")
public class AdminCommentController {

	public static final String REDIRECT_ADMIN_COMMENT_LIST = "redirect:/admin/comment/";
	public static final String REDIRECT_ADMIN_COMMENT_EDIT = "redirect:/admin/comment/edit/{code}/";

	private static final String LIST_VIEW = "admin/comment/list";
	private static final String FORM_VIEW = "admin/comment/form";

	private final CommentFacade commentFacade;

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
				"Fetching page comments. [{}={}]",
				"Pageable", pageable
		);
		val commentsPage = commentFacade.findAll(pageable);
		log.info(
				"Fetched comments. [{}={}, {}={}, {}={}]",
				"Total Pages", commentsPage.getTotalPages(),
				"Page number", commentsPage.getNumber(),
				"Comments list size", commentsPage.getContent().size()
		);
		model.addAttribute("comments", commentsPage.getContent());
		model.addAttribute("currentPage", commentsPage.getNumber());
		model.addAttribute("totalPages", commentsPage.getTotalPages());
		model.addAttribute("size", size);
		return LIST_VIEW;
	}

	@GetMapping("/edit/{code}/")
	@PreAuthorize("isAuthenticated()")
	public String edit(@PathVariable String code, Model model) {
		log.info(
				"Entering the Comment edit page. Parameters [{}={}]",
				"Code", code
		);

		commentFacade.findByCode(code).ifPresentOrElse(
				comment -> model.addAttribute("comment", comment),
				() -> throwCommentNotFound(code)
		);
		return FORM_VIEW;
	}

	@GetMapping("/create/")
	@PreAuthorize("isAuthenticated()")
	public String create(Model model) {
		log.info("Entering the new comment page.");
		model.addAttribute("comment", new CommentData());
		return FORM_VIEW;
	}

	@PostMapping("/save/")
	@PreAuthorize("isAuthenticated()")
	public String save(
			@Valid @ModelAttribute("comment") CommentData comment,
			BindingResult result,
			Model model
	) {
		log.info(
				"Saving the Comment. Parameters [{}={}]",
				"Comment", comment
		);
		if (result.hasErrors()) {
			model.addAttribute("comment", comment);
			return FORM_VIEW;
		}
		CommentData commentDataSaved = save(comment);
		return expand(REDIRECT_ADMIN_COMMENT_EDIT, Map.of("code", commentDataSaved.getCode()));
	}

	@PostMapping("/delete/{code}/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(@PathVariable String code) {
		log.info(
				"Deleting the comment. Parameters [{}={}]",
				"Code", code
		);
		if (!commentFacade.delete(code)) {
			throw commentNotFound(code);
		}
		return REDIRECT_ADMIN_COMMENT_LIST;
	}

	private CommentData save(CommentData comment) {
		log.info(
				"Save the comment. Parameters [{}={}]",
				"Code", comment.getCode()
		);
//        return commentFacade.save(comment);
		return CommentData.builder().build();
	}
}
