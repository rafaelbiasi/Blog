package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BlogRepository<Role> {

    Optional<Role> findByName(final String name);
}
