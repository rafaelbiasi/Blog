package br.com.rafaelbiasi.blog.transformer;

import java.util.List;

public interface MapperList<S, T> {

    List<Mapper<S, T>> getMappers();

    void setMappers(List<Mapper<S, T>> mappers);
}
