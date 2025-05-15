package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;

import java.util.List;

public interface PostService extends EntityService<PostModel> {

	List<PostModel> findAll();

	PageModel<PostModel> findAll(final PageRequestModel pageable);

}
