package br.com.rafaelbiasi.blog.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void init();

    void save(MultipartFile file);

    Resource load(String filename);
}
