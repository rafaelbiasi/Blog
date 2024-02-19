package br.com.rafaelbiasi.blog.service.impl;

public record RegistrationResponse(
        boolean usernameExists,
        boolean emailExists
) {

    public boolean success() {
        return !usernameExists && !emailExists;
    }

    public boolean failed() {
        return usernameExists || emailExists;
    }

}
