package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.model.User;
import lombok.Builder;

@Builder
public record UsersResult(User user, User admin, User guest) {
}

