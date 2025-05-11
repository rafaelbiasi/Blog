package br.com.rafaelbiasi.blog.domain.repository;

import org.springframework.core.io.Resource;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

@NoRepositoryBean
public interface FileRepository {

    void copy(
            final InputStream inputStream,
            final Path resolve
    ) throws IOException;

    void createDirectories() throws IOException;

    Path resolve(final String originalFilename);

    Resource getUrlResource(final URI uri) throws MalformedURLException;
}
