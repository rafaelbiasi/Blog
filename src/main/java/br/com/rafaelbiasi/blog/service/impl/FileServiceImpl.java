package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.repository.FileRepository;
import br.com.rafaelbiasi.blog.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void init() {
        try {
            fileRepository.createDirectories();
        } catch (IOException e) {
            log.error("Could not initialize root folder", e);
            throw new RuntimeException("Could not initialize root folder");
        }
    }

    @Override
    public void save(MultipartFile file) {
        requireNonNull(file, "File is null.");
        String originalFilename = file.getOriginalFilename();
        requireNonNull(originalFilename, "Original filename is null.");
        try {
            fileRepository.copy(file.getInputStream(), fileRepository.resolve(originalFilename));
        } catch (Exception e) {
            log.error("Could not save file: {}", originalFilename, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Resource load(String filename) {
        requireNonNull(filename, "Requested filename is null");
        try {
            Resource resource = fileRepository.getUrlResource(fileRepository.resolve(filename).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.warn("File is not readable or does not exist: {}", filename);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.error("Error while loading file: {}", filename, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
