package br.com.rafaelbiasi.blog.transformer;

import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;

public interface BidirectionalMapper<S, T> extends Mapper<S, T> {


    void reverseMap(T source, S target) throws ConversionException;
}
