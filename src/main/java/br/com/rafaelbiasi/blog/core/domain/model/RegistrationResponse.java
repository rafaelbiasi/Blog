package br.com.rafaelbiasi.blog.core.domain.model;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        boolean usernameExists,
        boolean emailExists) {

    public boolean success() {
        return !usernameExists && !emailExists;
    }

}
