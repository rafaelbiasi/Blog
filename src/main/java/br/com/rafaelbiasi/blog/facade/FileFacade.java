package br.com.rafaelbiasi.blog.facade;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileFacade {

    Resource load(String imageUri);

    void save(MultipartFile file);
}
