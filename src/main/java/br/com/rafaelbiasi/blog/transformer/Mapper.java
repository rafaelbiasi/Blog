package br.com.rafaelbiasi.blog.transformer;

import br.com.rafaelbiasi.blog.exception.ConversionException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

public interface Mapper<Source, Target> {

    void map(Source source, Target target) throws ConversionException;

    default <TargetProperty> void mapGet(
            Supplier<TargetProperty> getter,
            Consumer<TargetProperty> setter
    ) {
        ofNullable(getter.get()).ifPresent(setter);
    }

    default <TargetProperty> void mapGet(
            Supplier<TargetProperty> getter,
            Consumer<TargetProperty> setter,
            Runnable orElse
    ) {
        ofNullable(getter.get()).ifPresentOrElse(setter, orElse);
    }

    default <SourceProperty, TargetProperty> void mapGet(
            Supplier<SourceProperty> getter,
            Function<SourceProperty, TargetProperty> getterConverter,
            Consumer<TargetProperty> setter
    ) {
        ofNullable(getter.get()).map(getterConverter).ifPresent(setter);
    }

    default <SourceProperty, TargetProperty> void mapGet(
            Supplier<SourceProperty> getter,
            Function<SourceProperty, TargetProperty> getterConverter,
            Consumer<TargetProperty> setter,
            Runnable orElse
    ) {
        ofNullable(getter.get()).map(getterConverter).ifPresentOrElse(setter, orElse);
    }

}
