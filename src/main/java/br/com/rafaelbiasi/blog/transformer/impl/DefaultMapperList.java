package br.com.rafaelbiasi.blog.transformer.impl;

import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.MapperList;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Implements a composite {@link Mapper} that aggregates multiple mappers.
 * This implementation allows for the sequential application of multiple {@link Mapper} instances
 * to a source and target object pair. It also implements {@link MapperList} to manage the list of mappers
 * and {@link BeanNameAware} to allow Spring to inject the bean name.
 *
 * @param <S> the source type from which data is retrieved
 * @param <T> the target type to which data is mapped
 */
@Getter
@Setter
public class DefaultMapperList<S, T> implements Mapper<S, T>, MapperList<S, T>, BeanNameAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultMapperList.class);
    private List<Mapper<S, T>> mappers;
    private String beanName;

    /**
     * Maps data from a source object to a target object using all registered {@link Mapper} instances.
     * Each mapper is applied in the order they are added.
     *
     * @param source the source object from which to retrieve data
     * @param target the target object to which data is to be mapped
     */
    @Override
    public void map(final S source, final T target) {
        final List<Mapper<S, T>> mapperList = getMappers();
        if (mapperList == null) {
            return;
        }

        for (final Mapper<S, T> mapper : mapperList) {
            if (mapper != null) {
                mapper.map(source, target);
            }
        }
    }

    /**
     * Initializes the mapper list and removes any duplicate mappers to ensure each mapper is unique.
     * This method is annotated with {@link PostConstruct} to ensure it runs after the bean properties have been set.
     */
    @PostConstruct
    public void removeMappersDuplicates() {
        if (mappers != null && !mappers.isEmpty()) {
            final LinkedHashSet<Mapper<S, T>> uniqueMappers = new LinkedHashSet<>();
            for (final Mapper<S, T> mapper : mappers) {
                if (!uniqueMappers.add(mapper)) {
                    LOGGER.warn("Removing duplicate mapper: {}. Ensuring unique mapper for {}", mapper.getClass().getSimpleName(), getBeanName());
                }
            }
            this.mappers = new ArrayList<>(uniqueMappers);
        } else {
            LOGGER.warn("No mappers configured for {}.", getBeanName());
        }
    }
}
