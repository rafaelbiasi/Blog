package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    Page<User> findAll(PageRequest pageable);

    Optional<User> findOneByEmail(final String email);

    Optional<User> findOneByUsername(final String username);

    void registerUser(final User user);

    RegistrationResponse checkEmailAndUsernameExists(final User user);
}
