package br.com.rafaelbiasi.blog.infrastructure.repository;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

public interface FileRepository {

    void copy(
            final InputStream inputStream,
            final Path resolve
    ) throws IOException;

    void createDirectories() throws IOException;

    Path resolve(final String originalFilename);

    Resource getUrlResource(final URI uri) throws MalformedURLException;
}
