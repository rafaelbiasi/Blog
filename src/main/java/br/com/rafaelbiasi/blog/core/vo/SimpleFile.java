package br.com.rafaelbiasi.blog.core.vo;

import java.io.InputStream;

public class SimpleFile {

	private final String originalFilename;
	private final InputStream inputStream;

	public SimpleFile(String originalFilename, InputStream inputStream) {
		this.originalFilename = originalFilename;
		this.inputStream = inputStream;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}