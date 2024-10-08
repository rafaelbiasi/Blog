package br.com.rafaelbiasi.blog.domain.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

    void init();

    void save(final MultipartFile file) throws IOException;

    Optional<Resource> load(final String filename);
}
