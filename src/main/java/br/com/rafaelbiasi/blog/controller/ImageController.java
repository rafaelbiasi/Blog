package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.facade.FileFacade;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static br.com.rafaelbiasi.blog.exception.ResourceNotFoundExceptionFactory.imageFileNotFound;
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
    public ResponseEntity<Resource> image(@PathVariable("id") String imageUri) throws IOException {
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

    @SneakyThrows
    private ResponseEntity<Resource> resourceResponseEntity(Resource resource) {
        MediaType contentType = mediaType(resource);
        log.info(
                "Image fetched. [{}={}, {}={}, {}={} {}]",
                "File name", resource.getFilename(),
                "Content type", contentType,
                "Content length", resource.contentLength(), "bytes"
        );
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, inlineFilename(resource))
                .body(resource);
    }

    private MediaType mediaType(Resource image) {
        String mimeType = servletContext.getMimeType(image.getFilename());
        log.debug(
                "Getting image mime type. [{}={}, {}={}]",
                "File name", image.getFilename(),
                "Mine type", mimeType
        );
        return mimeType != null
                ? MediaType.parseMediaType(mimeType)
                : MediaType.APPLICATION_OCTET_STREAM;
    }

    private static String inlineFilename(Resource image) {
        return "inline; filename=\"" + image.getFilename() + "\"";
    }

}
