package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService extends EntityService<Role> {

    Optional<Role> findByName(final String name);

    List<Role> findAll();
}
