package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.facade.CommentFacade;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

import static br.com.rafaelbiasi.blog.infrastructure.util.ControllerUtil.expand;
import static br.com.rafaelbiasi.blog.ui.controller.PostController.REDIRECT_POST;

@Slf4j
@Controller
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {

	private final CommentFacade commentFacade;
	private final PostFacade postFacade;

	@PostMapping("/add/post/{postCode}/")
	@PreAuthorize("isAuthenticated()")
	public String add(
			final @Valid @ModelAttribute("comment") CommentData comment,
			final BindingResult result,
			final RedirectAttributes redirectAttributes,
			final @PathVariable String postCode,
			final Principal principal
	) {
		val postDataOpt = postFacade.findByCode(postCode);
		if (postDataOpt.isPresent()) {
			val postData = postDataOpt.get();
			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("comment", comment);
			} else {
				commentFacade.save(comment, postCode, principal);
			}
			return expand(REDIRECT_POST, Map.of(
					"code", postData.getCode(),
					"slugifiedTitle", postData.getSlugifiedTitle()
			));
		}
		throw ResourceNotFoundExceptionFactory.postNotFound(postCode);
	}

	@PostMapping("/delete/{commentCode}/post/{postCode}/")
	@PreAuthorize("isAuthenticated()")
	public String delete(
			final @PathVariable("commentCode") String commentCode,
			final @PathVariable("postCode") String postCode
	) {
		val postDataOpt = postFacade.findByCode(postCode);
		if (postDataOpt.isPresent()) {
			val postData = postDataOpt.get();
			commentFacade.delete(commentCode);
			return expand(REDIRECT_POST, Map.of(
					"code", postData.getCode(),
					"slugifiedTitle", postData.getSlugifiedTitle()
			));
		}
		throw ResourceNotFoundExceptionFactory.postNotFound(postCode);
	}
}