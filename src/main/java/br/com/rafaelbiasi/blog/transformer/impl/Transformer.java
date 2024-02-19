package br.com.rafaelbiasi.blog.transformer.impl;

import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.MapperList;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of {@link AbstractTransformer} that allows for the use of multiple {@link Mapper}s
 * to perform complex transformations from a source object of type S to a target object of type T.
 * This class also implements {@link MapperList} to manage a list of mappers, providing functionality
 * to apply a sequence of transformations and ensuring uniqueness among the registered mappers.
 *
 * @param <S> the source type from which data is retrieved
 * @param <T> the target type to which data is mapped
 */
@Slf4j
public class Transformer<S, T> extends AbstractTransformer<S, T> implements MapperList<S, T> {

    private List<Mapper<S, T>> mappers;

    /**
     * Constructs an EnhancedTransformer with a specified target class.
     * Initializes the internal list of {@link Mapper}s and sets the target class for transformation.
     *
     * @param targetClass the class of the target type T
     */
    public Transformer(Class<T> targetClass) {
        setTargetClass(targetClass);
        log.debug("Initializing EnhancedTransformer with target class: {}", targetClass.getSimpleName());
    }

    /**
     * Applies all configured {@link Mapper}s to map data from the source object to the target object.
     * Each mapper is applied in the order they are registered. Only non-null mappers are applied.
     *
     * @param source the source object from which data is retrieved
     * @param target the target object to which data is mapped
     */
    @Override
    public void map(final S source, final T target) {
        final List<Mapper<S, T>> mapperList = getMappers();
        if (mapperList == null) {
            log.debug("No mappers configured for transformation.");
            return;
        }
        log.debug("Applying {} mappers to source object of type {}", mapperList.size(), source.getClass().getSimpleName());
        mapperList.stream()
                .filter(Objects::nonNull)
                .forEach(mapper -> {
                    log.trace("Applying mapper: {} to source object", mapper.getClass().getSimpleName());
                    mapper.map(source, target);
                });
    }

    /**
     * Removes duplicate mappers from the internal list to ensure that each mapper is unique.
     * This method is called after bean properties are set and before the bean is used.
     */
    @PostConstruct
    public void removeMappersDuplicates() {
        if (mappers != null && !mappers.isEmpty()) {
            final LinkedHashSet<Mapper<S, T>> uniqueMappers = new LinkedHashSet<>();

            for (final Mapper<S, T> mapper : mappers) {
                if (!uniqueMappers.add(mapper)) {
                    log.warn("Removing duplicate mapper: {}. Ensuring unique mapper for {}", mapper.getClass().getSimpleName(), getBeanName());
                }
            }
            this.mappers = new ArrayList<>(uniqueMappers);
            log.debug("Mappers deduplication completed for {}. Total unique mappers: {}", getBeanName(), uniqueMappers.size());
        } else {
            log.warn("No mappers configured for {}.", getBeanName());
        }
    }

    /**
     * Returns the list of currently registered {@link Mapper}s.
     *
     * @return the list of mappers
     */
    @Override
    public List<Mapper<S, T>> getMappers() {
        return mappers;
    }

    /**
     * Sets the list of {@link Mapper}s to be used for mapping.
     * This method also logs the number of mappers set.
     *
     * @param mappers the list of entity mappers to be registered
     */
    @Override
    public void setMappers(final List<Mapper<S, T>> mappers) {
        this.mappers = mappers;
        log.debug("Set {} mappers for EnhancedTransformer {}", mappers != null ? mappers.size() : 0, getBeanName());
    }

}
