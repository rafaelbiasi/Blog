package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.core.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findOneByEmail(final String email);

    Optional<User> findOneByUsername(final String username);

    void registerUser(final User user);

    RegistrationResponse checkEmailAndUsernameExists(final User user);
}
