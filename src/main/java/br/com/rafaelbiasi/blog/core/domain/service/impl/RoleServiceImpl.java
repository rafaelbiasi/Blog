package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.Role;
import br.com.rafaelbiasi.blog.core.domain.repository.RoleRepository;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(final long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void delete(final Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Role save(final Role role) {
        requireNonNull(role, "The Role has a null value.");
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(final String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
