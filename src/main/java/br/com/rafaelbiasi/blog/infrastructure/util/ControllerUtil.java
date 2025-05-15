package br.com.rafaelbiasi.blog.infrastructure.util;

import org.springframework.web.util.UriTemplate;

import java.util.Map;

public class ControllerUtil {

	public static String expand(
			final String redirectPost,
			final Map<String, ?> uriVariables
	) {
		return new UriTemplate(redirectPost).expand(uriVariables).toString();
	}
}
