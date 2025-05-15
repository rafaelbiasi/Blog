package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.imageFileNotFound;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/images/")
public class ImageController {

	private final FileFacade fileService;
	private final ServletContext servletContext;

	@GetMapping("/{id}/")
	public ResponseEntity<Resource> image(final @PathVariable("id") String imageUri) {
		log.info(
				"Fetching image. Parameters [{}={}]",
				"Image URI", imageUri
		);
		return ofNullable(imageUri)
				.filter(not(String::isBlank))
				.flatMap(fileService::load)
				.map(this::resourceResponseEntity)
				.orElseThrow(() -> imageFileNotFound(imageUri));
	}

	private static String inlineFilename(final Resource image) {
		return "inline; filename=\"" + image.getFilename() + "\"";
	}

	private ResponseEntity<Resource> resourceResponseEntity(final Resource resource) {
		val contentType = mediaType(resource);
		log.info(
				"Image fetched. [{}={}, {}={}]",
				"File name", resource.getFilename(),
				"Content type", contentType
		);
		return ResponseEntity.ok()
				.contentType(contentType)
				.header(HttpHeaders.CONTENT_DISPOSITION, inlineFilename(resource))
				.body(resource);
	}

	private MediaType mediaType(final Resource resource) {
		val mimeType = servletContext.getMimeType(resource.getFilename());
		log.debug(
				"Getting image mime type. [{}={}, {}={}]",
				"File name", resource.getFilename(),
				"Mine type", mimeType
		);
		return mimeType != null
				? MediaType.parseMediaType(mimeType)
				: MediaType.APPLICATION_OCTET_STREAM;
	}

}
