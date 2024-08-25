package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @SneakyThrows
    public void init() {
        fileRepository.createDirectories();
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        requireNonNull(file, "The File has a null value.");
        String originalFilename = file.getOriginalFilename();
        requireNonNull(originalFilename, "The Original filename has a null value.");
        fileRepository.copy(file.getInputStream(), fileRepository.resolve(originalFilename));
    }

    @Override
    @SneakyThrows
    public Optional<Resource> load(String filename) {
        requireNonNull(filename, "The requested filename has a null value.");
        Resource resource = fileRepository.getUrlResource(fileRepository.resolve(filename).toUri());
        if (resource.exists() || resource.isReadable()) {
            return Optional.of(resource);
        }
        return Optional.empty();
    }
}
