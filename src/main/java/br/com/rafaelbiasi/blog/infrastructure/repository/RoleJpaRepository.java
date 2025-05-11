package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.model.Role;
import br.com.rafaelbiasi.blog.domain.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends BlogRepository<Role>, RoleRepository {

}
