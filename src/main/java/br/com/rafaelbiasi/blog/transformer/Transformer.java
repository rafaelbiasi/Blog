package br.com.rafaelbiasi.blog.transformer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static java.util.Optional.ofNullable;

@Slf4j
public class Transformer<S, T> extends AbstractTransformer<S, T> implements MapperList<S, T> {

    private List<Mapper<S, T>> mappers;

    public Transformer(Class<T> targetClass) {
        setTargetClass(targetClass);
        log.debug("Initializing EnhancedTransformer with target class: {}", targetClass.getSimpleName());
    }

    @Override
    public void map(final S source, final T target) {
        final Optional<List<Mapper<S, T>>> mapperList = ofNullable(getMappers());
        if (mapperList.isEmpty()) {
            log.debug("No mappers configured for transformation.");
            return;
        }
        log.debug(
                "Applying {} mappers to source object of type {}",
                getMappers().size(),
                source.getClass().getSimpleName()
        );
        getMappers().stream()
                .filter(Objects::nonNull)
                .forEach(mapper -> {
                    log.trace("Applying mapper: {} to source object", mapper.getClass().getSimpleName());
                    mapper.map(source, target);
                });
    }

    @Override
    public List<Mapper<S, T>> getMappers() {
        return mappers;
    }

    @Override
    public void setMappers(final List<Mapper<S, T>> mappers) {
        this.mappers = mappers;
        log.debug(
                "Set {} mappers for EnhancedTransformer {}",
                mappers != null ? mappers.size() : 0,
                getBeanName()
        );
    }

    @PostConstruct
    public void removeMappersDuplicates() {
        final Optional<List<Mapper<S, T>>> mapperList = ofNullable(getMappers());
        if (mapperList.isPresent()) {
            final LinkedHashSet<Mapper<S, T>> uniqueMappers = new LinkedHashSet<>();

            for (final Mapper<S, T> mapper : mapperList.get()) {
                if (!uniqueMappers.add(mapper)) {
                    log.warn(
                            "Removing duplicate mapper: {}. Ensuring unique mapper for {}",
                            mapper.getClass().getSimpleName(),
                            getBeanName()
                    );
                }
            }
            setMappers(new ArrayList<>(uniqueMappers));
            log.debug(
                    "Mappers deduplication completed for {}. Total unique mappers: {}",
                    getBeanName(),
                    uniqueMappers.size()
            );
        } else {
            log.warn("No mappers configured for {}.", getBeanName());
        }
    }

}
