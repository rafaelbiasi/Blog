package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.model.User;
import br.com.rafaelbiasi.blog.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends BlogRepository<User>, UserRepository {

}
