package br.com.rafaelbiasi.blog.infrastructure.persistence.repository;

import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.CommentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends BlogRepository<CommentEntity> {

}
