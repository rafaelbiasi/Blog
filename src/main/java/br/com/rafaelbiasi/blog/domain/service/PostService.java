package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService extends EntityService<Post> {

    List<Post> findAll();

    Page<Post> findAll(final Pageable pageable);

}
