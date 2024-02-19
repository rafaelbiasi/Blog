package br.com.rafaelbiasi.blog.facade.impl;

import br.com.rafaelbiasi.blog.facade.FileFacade;
import br.com.rafaelbiasi.blog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {

    private final FileService fileService;

    @Override
    public Resource load(String imageUri) {
        Objects.requireNonNull(imageUri, "ImageURI is null.");
        return fileService.load(imageUri);
    }

    @Override
    public void save(MultipartFile file) {
        Objects.requireNonNull(file, "MultipartFile is null.");
        fileService.save(file);
    }
}
