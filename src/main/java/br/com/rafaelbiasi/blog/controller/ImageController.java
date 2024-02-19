package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.facade.FileFacade;
import br.com.rafaelbiasi.blog.util.LogId;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileFacade fileService;
    private final ServletContext servletContext;

    /**
     * Fetches an image by its URI and serves it to the client.
     * The method determines the content type of the image and sets appropriate HTTP headers.
     *
     * @param imageUri the URI of the image to fetch, mapped from the URL path
     * @return a {@link ResponseEntity} containing the image as a {@link Resource} and HTTP headers
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> image(@PathVariable("id") String imageUri) {
        String logId = LogId.logId();
        log.info("#{}={}. Fetching image. Parameters [{}={}]",
                "LogID", logId,
                "Image URI", imageUri
        );
        try {
            if (imageUri != null && !imageUri.isEmpty()) {
                Resource image = fileService.load(imageUri);
                MediaType contentType = getMediaType(logId, image);
                log.info("#{}={}. Image fetched. [{}={}, {}={}, {}={} {}]",
                        "LogID", logId,
                        "File name", image.getFilename(),
                        "Content type", contentType,
                        "Content length", image.contentLength(), "bytes"
                );
                return ResponseEntity.ok()
                        .contentType(contentType)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFilename() + "\"")
                        .body(image);
            } else {
                log.error("#{}={}. Error loading image with null or empty URI. [{}={}]",
                        "LogID", logId,
                        "Image URI", imageUri);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("#{}={}. Exception encountered while loading image with URI. [{}={}]",
                    "LogID", logId,
                    "Image URI", imageUri, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private MediaType getMediaType(String logId, Resource image) {
        String mimeType = servletContext.getMimeType(image.getFilename());
        log.debug("#{}={}. Getting image mime type. [{}={}, {}={}]",
                "LogID", logId,
                "File name", image.getFilename(),
                "Mine type", mimeType
        );
        return mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;
    }
}
