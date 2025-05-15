package br.com.rafaelbiasi.blog.ui.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/")
public class CustomErrorController implements ErrorController {

	private static final Map<Integer, String> ERROR_VIEW_MAP = createErrorViewMap();

	@RequestMapping("/error/")
	public String handleError(final HttpServletRequest request) {
		val statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
		return ERROR_VIEW_MAP.getOrDefault(statusCode, "error/generic");
	}

	private static Map<Integer, String> createErrorViewMap() {
		return Map.of(
				403, "error/403",
				404, "error/404",
				500, "error/500"
		);
	}
}
