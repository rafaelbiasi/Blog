package br.com.rafaelbiasi.blog.infrastructure.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public static ResourceNotFoundException of(final String message) {
		return new ResourceNotFoundException(message);
	}
}