package br.com.rafaelbiasi.blog.transformer;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@Setter
public class DefaultMapperList<S, T> implements Mapper<S, T>, MapperList<S, T>, BeanNameAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultMapperList.class);
    private List<Mapper<S, T>> mappers;
    private String beanName;

    @Override
    public void map(final S source, final T target) {
        final Optional<List<Mapper<S, T>>> mapperList = ofNullable(getMappers());
        if (mapperList.isEmpty()) {
            return;
        }

        for (final Mapper<S, T> mapper : mapperList.get()) {
            if (mapper != null) {
                mapper.map(source, target);
            }
        }
    }

    @PostConstruct
    public void removeMappersDuplicates() {
        final Optional<List<Mapper<S, T>>> mapperList = ofNullable(getMappers());
        mapperList.ifPresentOrElse(list -> {
                    final LinkedHashSet<Mapper<S, T>> uniqueMappers = new LinkedHashSet<>();
                    for (final Mapper<S, T> mapper : list) {
                        if (!uniqueMappers.add(mapper)) {
                            LOGGER.warn(
                                    "Removing duplicate mapper: {}. Ensuring unique mapper for {}",
                                    mapper.getClass().getSimpleName(),
                                    getBeanName()
                            );
                        }
                    }
                    setMappers(new ArrayList<>(uniqueMappers));
                }, () -> LOGGER.warn("No mappers configured for {}.", getBeanName())
        );
    }
}
