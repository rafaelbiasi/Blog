package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.domain.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {

    private final FileService fileService;

    @Override
    public Optional<Resource> load(final String imageUri) {
        requireNonNull(imageUri, "The ImageURI has a null value.");
        return fileService.load(imageUri);
    }

    @Override
    @SneakyThrows
    public void save(final MultipartFile file) {
        requireNonNull(file, "The MultipartFile has a null value.");
        fileService.save(file);
    }
}
