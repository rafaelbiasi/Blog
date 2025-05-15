package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.postNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwPostNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.util.ControllerUtil.expand;
import static java.util.Optional.ofNullable;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post/")
public class PostController {

	public static final String REDIRECT_POST = "redirect:/post/{code}/{slugifiedTitle}/";

	private static final String POST_VIEW = "post/post";

	private final PostFacade postFacade;

	@GetMapping("/{code}/{slugifiedTitle}/")
	public String post(
			final @PathVariable String code,
			final @PathVariable String slugifiedTitle,
			final Model model,
			final HttpServletRequest request
	) {
		log.info(
				"Entering the post page. Parameters [{}={}]",
				"Code", code
		);
		postFacade.findByCode(code).ifPresentOrElse(
				post -> addToModelAttribute(model, request, post),
				() -> throwPostNotFound(code)
		);
		return POST_VIEW;
	}

	@GetMapping("/{code}/")
	public String post(
			final @PathVariable String code,
			final Model model,
			final HttpServletRequest request
	) {
		PostData postData = postFacade.findByCode(code).orElseThrow(() -> postNotFound(code));
		return expand(REDIRECT_POST, Map.of(
				"code", postData.getCode(),
				"slugifiedTitle", postData.getSlugifiedTitle()
		));
	}

	private void addToModelAttribute(
			final Model model,
			final HttpServletRequest request,
			final PostData post
	) {
		model.addAttribute("post", post);
		addFlashAttributeWhenError(model, request);
		addCommentAttribute(model);
	}

	private void addFlashAttributeWhenError(
			final Model model,
			final HttpServletRequest request
	) {
		ofNullable(RequestContextUtils.getInputFlashMap(request)).ifPresent(model::addAllAttributes);
	}

	private void addCommentAttribute(final Model model) {
		if (!model.containsAttribute("comment")) {
			model.addAttribute("comment", new CommentData());
		}
	}
}
