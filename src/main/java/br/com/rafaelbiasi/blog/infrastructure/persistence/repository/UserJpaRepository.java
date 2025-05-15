package br.com.rafaelbiasi.blog.infrastructure.persistence.repository;

import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends BlogRepository<UserEntity> {

	Optional<UserEntity> findOneByEmailIgnoreCase(String email);

	Optional<UserEntity> findOneByUsernameIgnoreCase(String username);
}
