package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BlogRepository<Role> {

    Optional<Role> findByName(String name);
}
