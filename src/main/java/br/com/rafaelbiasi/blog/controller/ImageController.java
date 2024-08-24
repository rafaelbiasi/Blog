package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.exception.ResourceNotFoundException;
import br.com.rafaelbiasi.blog.facade.FileFacade;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileFacade fileService;
    private final ServletContext servletContext;

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> image(@PathVariable("id") String imageUri) throws IOException {
        log.info("Fetching image. Parameters [{}={}]",
                "Image URI", imageUri
        );
        Optional<String> imageUriOpt = ofNullable(imageUri).filter(not(String::isBlank));
        if (imageUriOpt.isPresent()) {
            Resource image = fileService.load(imageUriOpt.get());
            MediaType contentType = getMediaType(image);
            log.info("Image fetched. [{}={}, {}={}, {}={} {}]",
                    "File name", image.getFilename(),
                    "Content type", contentType,
                    "Content length", image.contentLength(), "bytes"
            );
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFilename() + "\"")
                    .body(image);
        } else {
            throw new ResourceNotFoundException("Error loading image with null or empty URI. [Image URI=" + imageUri + "] ");
        }
    }

    private MediaType getMediaType(Resource image) {
        String mimeType = servletContext.getMimeType(image.getFilename());
        log.debug("Getting image mime type. [{}={}, {}={}]",
                "File name", image.getFilename(),
                "Mine type", mimeType
        );
        return mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;
    }
}
