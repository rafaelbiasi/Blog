package br.com.rafaelbiasi.blog.core.domain.model;

import java.io.InputStream;

public class SimpleResource {

    private final String filename;
    private final InputStream inputStream;

    public SimpleResource(String filename, InputStream inputStream) {
        this.filename = filename;
        this.inputStream = inputStream;
    }

    public String getFilename() {
        return filename;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}