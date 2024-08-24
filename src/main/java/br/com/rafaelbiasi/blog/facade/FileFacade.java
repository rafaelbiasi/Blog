package br.com.rafaelbiasi.blog.facade;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileFacade {

    Optional<Resource> load(String imageUri);

    void save(MultipartFile file);
}
