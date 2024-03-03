package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Role;

import java.util.Optional;

public interface RoleService extends EntityService<Role> {

    Optional<Role> findByName(String name);
}
