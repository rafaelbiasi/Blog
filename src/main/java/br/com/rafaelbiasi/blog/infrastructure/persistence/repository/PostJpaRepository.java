package br.com.rafaelbiasi.blog.infrastructure.persistence.repository;

import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.PostEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends BlogRepository<PostEntity> {

}
