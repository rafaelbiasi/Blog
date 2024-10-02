package br.com.rafaelbiasi.blog.domain.model;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        boolean usernameExists,
        boolean emailExists) {

    public boolean success() {
        return !usernameExists && !emailExists;
    }

}
