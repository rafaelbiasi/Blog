package br.com.rafaelbiasi.blog.repository.impl;

import br.com.rafaelbiasi.blog.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final Path root;

    public FileRepositoryImpl() {
        this(Paths.get("./uploads"));
    }

    @Override
    public void copy(InputStream inputStream, Path target) throws IOException {
        Files.copy(inputStream, target);
    }

    @Override
    public void createDirectories() throws IOException {
        Files.createDirectories(root);
    }

    @Override
    public Path resolve(String originalFilename) {
        return root.resolve(originalFilename);
    }

    @Override
    public Resource getUrlResource(URI uri) throws MalformedURLException {
        return new UrlResource(uri);
    }
}
