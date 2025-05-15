package br.com.rafaelbiasi.blog.core.domain.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

public interface FileService {

	void init() throws IOException;

	void save(final MultipartFile file) throws IOException;

	Optional<Resource> load(final String filename) throws MalformedURLException;
}
