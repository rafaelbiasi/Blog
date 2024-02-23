package br.com.rafaelbiasi.blog.transformer;

import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides a generic interface for mapping entities from a source type S to a target type T.
 * This interface defines methods for transferring data between different object models,
 * with support for custom getter methods and transformation functions to accommodate complex mapping scenarios.
 *
 * @param <S> the source type from which data is retrieved
 * @param <T> the target type to which data is mapped
 */
public interface Mapper<S, T> {

    /**
     * Maps data from a source object to a target object.
     *
     * @param source the source object from which to retrieve data
     * @param target the target object to which data is to be mapped
     * @throws ConversionException if an error occurs during the mapping process
     */
    void map(S source, T target) throws ConversionException;

    /**
     * Maps a value retrieved by a {@link Supplier} to a {@link Consumer}, typically used for setting a value on the target object.
     *
     * @param <TT>   the type of the value to be mapped
     * @param getter a {@link Supplier} that provides the value to be mapped
     * @param setter a {@link Consumer} that accepts the mapped value
     */
    default <TT> void mapGet(Supplier<TT> getter, Consumer<TT> setter) {
        Optional.ofNullable(getter.get()).ifPresent(setter);
    }

    /**
     * Maps a value retrieved by a {@link Supplier} to a {@link Consumer}, with an additional {@link Runnable} executed if the value is null.
     *
     * @param <TT>   the type of the value to be mapped
     * @param getter a {@link Supplier} that provides the value to be mapped
     * @param setter a {@link Consumer} that accepts the mapped value
     * @param orElse a {@link Runnable} to be executed if the value provided by getter is null
     */
    default <TT> void mapGet(Supplier<TT> getter, Consumer<TT> setter, Runnable orElse) {
        Optional.ofNullable(getter.get()).ifPresentOrElse(setter, orElse);
    }

    /**
     * Maps a value retrieved by a {@link Supplier}, transforms it using a {@link Function}, and then applies it to a {@link Consumer}.
     *
     * @param <ST>            the source type of the value to be mapped
     * @param <TT>            the target type of the mapped value
     * @param getter          a {@link Supplier} that provides the source value
     * @param getterConverter a {@link Function} that transforms the source value to the target value type
     * @param setter          a {@link Consumer} that accepts the transformed value
     */
    default <ST, TT> void mapGet(Supplier<ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter) {
        Optional.ofNullable(getter.get()).map(getterConverter).ifPresent(setter);
    }

    /**
     * Maps a value retrieved by a {@link Supplier}, transforms it using a {@link Function}, and then applies it to a {@link Consumer}.
     * If the value is null, an additional {@link Runnable} is executed.
     *
     * @param <ST>            the source type of the value to be mapped
     * @param <TT>            the target type of the mapped value
     * @param getter          a {@link Supplier} that provides the source value
     * @param getterConverter a {@link Function} that transforms the source value to the target value type
     * @param setter          a {@link Consumer} that accepts the transformed value
     * @param orElse          a {@link Runnable} to be executed if the value provided by getter is null
     */
    default <ST, TT> void mapGet(Supplier<ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter, Runnable orElse) {
        Optional.ofNullable(getter.get()).map(getterConverter).ifPresentOrElse(setter, orElse);
    }

    /**
     * @deprecated Use {@link #mapGet(Supplier, Consumer)} instead.
     */
    @Deprecated(forRemoval = true)
    default <S, TT> void mapSource(S source, Function<S, TT> getter, Consumer<TT> setter) {
        Optional.of(source).map(getter).ifPresent(setter);
    }

    /**
     * @deprecated Use {@link #mapGet(Supplier, Consumer, Runnable)} instead.
     */
    @Deprecated(forRemoval = true)
    default <S, TT> void mapSource(S source, Function<S, TT> getter, Consumer<TT> setter, Runnable orElse) {
        Optional.of(source).map(getter).ifPresentOrElse(setter, orElse);
    }

    /**
     * @deprecated Use {@link #mapGet(Supplier, Function, Consumer)} instead.
     */
    @Deprecated(forRemoval = true)
    default <S, ST, TT> void mapSource(S source, Function<S, ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter) {
        Optional.of(source).map(getter).map(getterConverter).ifPresent(setter);
    }

    /**
     * @deprecated Use {@link #mapGet(Supplier, Function, Consumer, Runnable)} instead.
     */
    @Deprecated(forRemoval = true)
    default <S, ST, TT> void mapSource(S source, Function<S, ST> getter, Function<ST, TT> getterConverter, Consumer<TT> setter, Runnable orElse) {
        Optional.of(source).map(getter).map(getterConverter).ifPresentOrElse(setter, orElse);
    }
}
