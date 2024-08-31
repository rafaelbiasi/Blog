package br.com.rafaelbiasi.blog.exception;

import java.io.FileNotFoundException;

public class ResourceNotFoundExceptionFactory {

    public static void throwCommentNotFound(String code) {
        throw commentNotFound(code);
    }

    public static ResourceNotFoundException commentNotFound(String code) {
        return ResourceNotFoundException.of("Comment not found for [code=" + code + "]");
    }

    public static void throwPostNoFound(String code) {
        throw postNoFound(code);
    }

    public static ResourceNotFoundException postNoFound(String code) {
        return ResourceNotFoundException.of("Post not found for [code=" + code + "]");
    }

    public static void throwAccountNotFound(String username) {
        throw accountNotFound(username);
    }

    public static ResourceNotFoundException accountNotFound(String username) {
        return ResourceNotFoundException.of("Account not found for [username=" + username + "]");
    }

    public static void throwImageFileNotFound(String imageUri) throws FileNotFoundException {
        throw imageFileNotFound(imageUri);
    }

    public static ResourceNotFoundException imageFileNotFound(String imageUri) {
        return ResourceNotFoundException.of(
                "Error loading image with null or empty URI. [Image URI=%s] ".formatted(imageUri)
        );
    }

}
