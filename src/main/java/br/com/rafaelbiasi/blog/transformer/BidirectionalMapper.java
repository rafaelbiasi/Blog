package br.com.rafaelbiasi.blog.transformer;

public interface BidirectionalMapper<S, T> extends Mapper<S, T> {


    void reverseMap(T source, S target) throws ConversionException;
}
