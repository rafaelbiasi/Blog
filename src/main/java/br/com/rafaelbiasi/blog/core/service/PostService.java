package br.com.rafaelbiasi.blog.core.service;

import br.com.rafaelbiasi.blog.core.model.Post;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.List;

public interface PostService extends EntityService<Post> {

	List<Post> findAll();

	SimplePage<Post> findAll(final SimplePageRequest pageable);

}
