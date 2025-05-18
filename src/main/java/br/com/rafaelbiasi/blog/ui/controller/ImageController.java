package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.core.vo.SimpleResource;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
	public ResponseEntity<InputStreamResource> image(@PathVariable("id") String imageUri) throws IOException {
		log.info("Fetching image. Parameters [{}={}]", "Image URI", imageUri);
		SimpleResource resource = ofNullable(imageUri)
				.filter(not(String::isBlank))
				.flatMap(fileService::load)
				.orElseThrow(() -> imageFileNotFound(imageUri));

		String contentType = mediaType(resource.getFilename());
		log.info("Image fetched. [{}={}, {}={}]", "File name", resource.getFilename(), "Content type", contentType);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(new InputStreamResource(resource.getInputStream()));
	}

	private String mediaType(String filename) {
		String mimeType = servletContext.getMimeType(filename);
		return mimeType != null ? mimeType : MediaType.APPLICATION_OCTET_STREAM_VALUE;
	}
}