package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService extends EntityService<Post> {

    List<Post> findAll();

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findByCode(String code);
}
