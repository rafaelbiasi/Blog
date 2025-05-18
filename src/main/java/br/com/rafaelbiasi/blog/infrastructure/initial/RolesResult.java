package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.model.Role;
import lombok.Builder;

@Builder
public record RolesResult(Role user, Role admin, Role guest) {
}