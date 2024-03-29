package br.com.rafaelbiasi.blog.transformer.impl;

import br.com.rafaelbiasi.blog.transformer.Converter;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@Getter
@Setter
public abstract class AbstractTransformer<S, T> implements Converter<S, T>, Mapper<S, T>, InitializingBean, BeanNameAware {

    private String beanName;
    @Getter(AccessLevel.PRIVATE)
    private Class<T> targetClass;

    @Override
    public abstract void map(final S source, final T target);

    protected T createFromClass() {
        try {
            T instance = targetClass.getDeclaredConstructor().newInstance();
            log.debug("Created new instance of target class: {}", targetClass.getSimpleName());
            return instance;
        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException e) {
            log.error("Error creating instance of target class: {}", targetClass.getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    public T convertTo(S source, T target) {
        log.debug("Converting/mapping source object of type {} into target object of type {}", source.getClass().getSimpleName(), target.getClass().getSimpleName());
        map(source, target);
        log.debug("Successfully mapped/converted source object into target object");
        return target;
    }

    public T convert(S source) {
        log.debug("Converting source object of type {} into new target object of type {}", source.getClass().getSimpleName(), targetClass.getSimpleName());
        T target = createFromClass();
        map(source, target);
        log.debug("Successfully converted source object into new target object");
        return target;
    }

    public void setTargetClass(Class<T> targetClass) {
        Optional<Class<T>> targetClassOpt = ofNullable(targetClass);
        if (targetClassOpt.isPresent()) {
            log.debug("Setting target class for transformer: {}", targetClass.getSimpleName());
            this.targetClass = targetClass;
            log.debug("Creating test instance of target class: {}", targetClass.getSimpleName());
            createFromClass();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Empty
    }
}
