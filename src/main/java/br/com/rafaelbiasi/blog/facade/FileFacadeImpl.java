package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.service.FileService;
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
    public Optional<Resource> load(String imageUri) {
        requireNonNull(imageUri, "ImageURI is null.");
        return fileService.load(imageUri);
    }

    @Override
    @SneakyThrows
    public void save(MultipartFile file) {
        requireNonNull(file, "MultipartFile is null.");
        fileService.save(file);
    }
}
