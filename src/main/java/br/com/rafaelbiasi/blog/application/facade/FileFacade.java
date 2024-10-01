package br.com.rafaelbiasi.blog.application.facade;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileFacade {

    Optional<Resource> load(final String imageUri);

    void save(final MultipartFile file);
}
