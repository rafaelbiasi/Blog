package br.com.rafaelbiasi.blog.specification.impl;

import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.specification.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsNewPostSpecification implements Specification<Post> {
    @Override
    public boolean isSatisfiedBy(Post post) {
        return post.getCode() == null;
    }
}
