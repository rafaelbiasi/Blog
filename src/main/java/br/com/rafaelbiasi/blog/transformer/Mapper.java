package br.com.rafaelbiasi.blog.transformer;

import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public interface Mapper<S, T> {

    void map(S source, T target) throws ConversionException;

    default <TT> void mapGet(Supplier<TT> getter, Consumer<TT> setter) {
        ofNullable(getter.get()).ifPresent(setter);
    }

    default <TT> void mapGet(Supplier<TT> getter, Consumer<TT> setter, Runnable orElse) {
        ofNullable(getter.get()).ifPresentOrElse(setter, orElse);
    }

    default <ST, TT> void mapGet(Supplier<ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter) {
        ofNullable(getter.get()).map(getterConverter).ifPresent(setter);
    }

    default <ST, TT> void mapGet(Supplier<ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter, Runnable orElse) {
        ofNullable(getter.get()).map(getterConverter).ifPresentOrElse(setter, orElse);
    }

    @Deprecated(forRemoval = true)
    default <S, TT> void mapSource(S source, Function<S, TT> getter, Consumer<TT> setter) {
        of(source).map(getter).ifPresent(setter);
    }

    @Deprecated(forRemoval = true)
    default <S, TT> void mapSource(S source, Function<S, TT> getter, Consumer<TT> setter, Runnable orElse) {
        of(source).map(getter).ifPresentOrElse(setter, orElse);
    }

    @Deprecated(forRemoval = true)
    default <S, ST, TT> void mapSource(S source, Function<S, ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter) {
        of(source).map(getter).map(getterConverter).ifPresent(setter);
    }

    @Deprecated(forRemoval = true)
    default <S, ST, TT> void mapSource(S source, Function<S, ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter, Runnable orElse) {
        of(source).map(getter).map(getterConverter).ifPresentOrElse(setter, orElse);
    }
}
