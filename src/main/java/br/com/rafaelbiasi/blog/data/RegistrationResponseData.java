package br.com.rafaelbiasi.blog.data;

import lombok.Builder;

@Builder
public record RegistrationResponseData(
        boolean usernameExists,
        boolean emailExists) {

    public boolean success() {
        return !usernameExists && !emailExists;
    }

  }