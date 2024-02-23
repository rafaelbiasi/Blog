package br.com.rafaelbiasi.blog.specification.impl;

import br.com.rafaelbiasi.blog.specification.Specification;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceIsReadableSpecification implements Specification<Resource> {
    @Override
    public boolean isSatisfiedBy(Resource candidate) {
        return candidate.isReadable();
    }
}
