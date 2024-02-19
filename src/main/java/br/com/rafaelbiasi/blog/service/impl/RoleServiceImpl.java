package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.repository.RoleRepository;
import br.com.rafaelbiasi.blog.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> getById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        Objects.requireNonNull(role, "Role is null.");
        return roleRepository.save(role);
    }
}
