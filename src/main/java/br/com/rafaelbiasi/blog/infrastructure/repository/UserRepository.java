package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BlogRepository<User> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByUsernameIgnoreCase(String username);
}
