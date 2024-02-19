package br.com.rafaelbiasi.blog.transformer;

import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;

public interface BidirectionalMapper<S, T> extends Mapper<S, T> {


    /**
     * Maps data from a source object to a target object.
     *
     * @param source the source object from which to retrieve data
     * @param target the target object to which data is to be mapped
     * @throws ConversionException if an error occurs during the mapping process
     */
    void reverseMap(T source, S target) throws ConversionException;
}
