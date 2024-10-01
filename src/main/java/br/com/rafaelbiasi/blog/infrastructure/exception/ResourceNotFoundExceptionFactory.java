package br.com.rafaelbiasi.blog.infrastructure.exception;


import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;

import java.io.FileNotFoundException;

public class ResourceNotFoundExceptionFactory {

    public static void throwCommentNotFound(final String code) {
        throw commentNotFound(code);
    }

    public static ResourceNotFoundException commentNotFound(final String code) {
        return ResourceNotFoundException.of("Comment not found for [code=" + code + "]");
    }

    public static void throwPostNotFound(final long code) {
        throw postNotFound(code);
    }

    public static ResourceNotFoundException postNotFound(final long code) {
        return ResourceNotFoundException.of("Post not found for [id=" + code + "]");
    }

    public static void throwPostNotFound(final String code) {
        throw postNotFound(code);
    }

    public static ResourceNotFoundException postNotFound(final String code) {
        return ResourceNotFoundException.of("Post not found for [id=" + SqidsUtil.decodeId(code) + "]");
    }

    public static void throwAccountNotFound(final String username) {
        throw accountNotFound(username);
    }

    public static ResourceNotFoundException accountNotFound(final String username) {
        return ResourceNotFoundException.of("Account not found for [username=" + username + "]");
    }

    public static void throwImageFileNotFound(final String imageUri) {
        throw imageFileNotFound(imageUri);
    }

    public static ResourceNotFoundException imageFileNotFound(final String imageUri) {
        return ResourceNotFoundException.of(
                "Error loading image with null or empty URI. [Image URI=%s] ".formatted(imageUri)
        );
    }

}
