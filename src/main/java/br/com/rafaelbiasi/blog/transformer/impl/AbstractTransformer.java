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

/**
 * Provides a base implementation for transforming objects from a source type S to a target type T.
 * This abstract class implements {@link Converter} and {@link Mapper} interfaces,
 * facilitating the conversion and mapping of data between different object models.
 * It also implements {@link InitializingBean} and {@link BeanNameAware} for integration with Spring's lifecycle callbacks.
 *
 * @param <S> the source type from which data is retrieved
 * @param <T> the target type to which data is mapped
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractTransformer<S, T> implements Converter<S, T>, Mapper<S, T>, InitializingBean, BeanNameAware {

    private String beanName;
    @Getter(AccessLevel.PRIVATE)
    private Class<T> targetClass;

    /**
     * Abstract method to map data from a source object to a target object.
     * Implementations should define the specific mapping logic.
     *
     * @param source the source object from which to retrieve data
     * @param target the target object to which data is to be mapped
     */
    @Override
    public abstract void map(final S source, final T target);

    /**
     * Creates an instance of the target class using its default constructor.
     * This method is used internally to instantiate target objects for conversion.
     *
     * @return an instance of the target class
     * @throws RuntimeException if instantiation fails due to reflection errors
     */
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

    /**
     * Converts and maps data from a source object to an existing target object.
     *
     * @param source the source object from which data is retrieved
     * @param target the target object to which data is to be mapped
     * @return the target object with mapped data
     */
    public T convertTo(S source, T target) {
        log.debug("Converting/mapping source object of type {} into target object of type {}", source.getClass().getSimpleName(), target.getClass().getSimpleName());
        map(source, target);
        log.debug("Successfully mapped/converted source object into target object");
        return target;
    }

    /**
     * Converts and maps data from a source object to a new instance of the target class.
     *
     * @param source the source object from which data is retrieved
     * @return a new instance of the target class with mapped data
     */
    public T convert(S source) {
        log.debug("Converting source object of type {} into new target object of type {}", source.getClass().getSimpleName(), targetClass.getSimpleName());
        T target = createFromClass();
        map(source, target);
        log.debug("Successfully converted source object into new target object");
        return target;
    }

    /**
     * Sets the target class for this transformer and creates a test instance to verify its constructability.
     *
     * @param targetClass the class object representing the target type
     */
    public void setTargetClass(Class<T> targetClass) {
        if (targetClass != null) {
            log.debug("Setting target class for transformer: {}", targetClass.getSimpleName());
            this.targetClass = targetClass;
            log.debug("Creating test instance of target class: {}", targetClass.getSimpleName());
            createFromClass();
        }
    }

    /**
     * Callback for bean initialization. This method is called after all bean properties have been set.
     * Implementations can override this method to perform any necessary initialization after property setting.
     *
     * @throws Exception in case of initialization errors
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //Empty
    }
}
