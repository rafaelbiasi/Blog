package br.com.rafaelbiasi.blog.specification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class NotSpecification<T> implements Specification<T> {

    private final Specification<T> other;

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !other.isSatisfiedBy(candidate);
    }
}
