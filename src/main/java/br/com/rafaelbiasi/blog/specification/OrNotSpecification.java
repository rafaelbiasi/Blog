package br.com.rafaelbiasi.blog.specification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class OrNotSpecification<T> implements Specification<T> {

    private final Specification<T> left;
    private final Specification<T> right;

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return left.isSatisfiedBy(candidate) || !right.isSatisfiedBy(candidate);
    }
}
