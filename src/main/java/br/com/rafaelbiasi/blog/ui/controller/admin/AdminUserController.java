package br.com.rafaelbiasi.blog.ui.controller.admin;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.UserFacade;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwUserNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.userNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.util.ControllerUtil.expand;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user/")
public class AdminUserController {

	public static final String REDIRECT_ADMIN_USER_LIST = "redirect:/admin/user/";
	public static final String REDIRECT_ADMIN_USER_EDIT = "redirect:/admin/user/edit/{code}/";

	private static final String LIST_VIEW = "admin/user/list";
	private static final String FORM_VIEW = "admin/user/form";

	private final UserFacade userFacade;

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
		val pageable = SimplePageRequest.of(page, size);
		log.debug(
				"Fetching page users. [{}={}]",
				"Pageable", pageable
		);
		val usersPage = userFacade.findAll(pageable);
		val content = usersPage.content();
		val number = usersPage.number();
		val totalPages = usersPage.totalPages();
		log.info(
				"Fetched users. [{}={}, {}={}, {}={}]",
				"Total Pages", totalPages,
				"Page number", number,
				"Users list size", content.size()
		);
		model.addAttribute("users", content)
				.addAttribute("currentPage", number)
				.addAttribute("totalPages", totalPages)
				.addAttribute("size", size);
		return LIST_VIEW;
	}

	@GetMapping("/edit/{code}/")
	@PreAuthorize("isAuthenticated()")
	public String edit(@PathVariable String code, Model model) {
		log.info(
				"Entering the User edit page. Parameters [{}={}]",
				"Code", code
		);

		List<RoleData> roles = userFacade.listAllRoles();
		model.addAttribute("roles", roles);
		userFacade.findByCode(code).ifPresentOrElse(
				user -> model.addAttribute("user", user),
				() -> throwUserNotFound(code)
		);
		return FORM_VIEW;
	}

	@GetMapping("/create/")
	@PreAuthorize("isAuthenticated()")
	public String create(Model model) {
		log.info("Entering the new user page.");
		List<RoleData> roles = userFacade.listAllRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("user", new UserData());
		return FORM_VIEW;
	}

	@PostMapping("/save/")
	@PreAuthorize("isAuthenticated()")
	public String save(
			@Valid @ModelAttribute("user") UserData user,
			BindingResult result,
			Model model
	) {
		log.info(
				"Saving the User. Parameters [{}={}]",
				"User", user
		);
		if (result.hasErrors()) {
			List<RoleData> roles = userFacade.listAllRoles();
			model.addAttribute("roles", roles);
			model.addAttribute("user", user);
			return FORM_VIEW;
		}
		save(user);
		return REDIRECT_ADMIN_USER_LIST;
	}

	@PostMapping("/delete/{code}/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(@PathVariable String code) {
		log.info(
				"Deleting the user. Parameters [{}={}]",
				"Code", code
		);
		if (!userFacade.delete(code)) {
			throw userNotFound(code);
		}
		return REDIRECT_ADMIN_USER_LIST;
	}

	private UserData save(UserData user) {
		log.info(
				"Save the user. Parameters [{}={}]",
				"Code", user.getCode()
		);
		return userFacade.save(user);
	}
}
