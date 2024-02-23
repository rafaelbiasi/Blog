package br.com.rafaelbiasi.blog.repository;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

public interface FileRepository {

    void copy(InputStream inputStream, Path resolve) throws IOException;

    void createDirectories() throws IOException;

    Path resolve(String originalFilename);

    Resource getUrlResource(URI uri) throws MalformedURLException;
}
