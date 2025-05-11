package br.com.rafaelbiasi.blog.infrastructure.persistence.repository;

import br.com.rafaelbiasi.blog.core.domain.model.Role;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends BlogRepository<RoleEntity> {

	Optional<RoleEntity> findByName(String name);
}
