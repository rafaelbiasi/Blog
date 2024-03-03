package br.com.rafaelbiasi.blog.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Optional.ofNullable;


public interface Converter<S, T> {

    Logger log = LoggerFactory.getLogger(Converter.class);

    T convert(S source);

    T convertTo(S source, T target);

    default List<T> convertAll(List<S> sources) {
        log.debug("Converting list of sources. Total elements: {}", sources == null ? 0 : sources.size());

        Optional<List<S>> sourcesOpt = ofNullable(sources);
        if (sourcesOpt.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> targets = new ArrayList<>(sources.size());
        for (S source : sources) {
            log.trace("Converting element: {}", source);
            T target = convert(source);
            targets.add(target);
            log.trace("Element converted successfully: {}", target);
        }
        log.debug("Completed conversion of list. Total converted elements: {}", targets.size());
        return targets;
    }

    default Set<T> convertAll(Set<S> sources) {
        log.debug("Converting set of sources. Total elements: {}", sources == null ? 0 : sources.size());
        Optional<Set<S>> sourcesOpt = ofNullable(sources);
        if (sourcesOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<T> targets = new HashSet<>(sources.size());
        for (S source : sources) {
            log.trace("Converting element: {}", source);
            T target = convert(source);
            targets.add(target);
            log.trace("Element converted successfully: {}", target);
        }
        log.debug("Completed conversion of set. Total converted elements: {}", targets.size());
        return targets;
    }

    default List<T> convertAllIgnoreExceptions(List<S> sources) {
        Optional<List<S>> sourcesOpt = ofNullable(sources);
        if (sourcesOpt.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> targets = new ArrayList<>();
        for (S source : sources) {
            try {
                T target = convert(source);
                targets.add(target);
            } catch (Exception e) {
                log.warn("Element conversion failed: " + source + ". Reason: " + e.getMessage(), e);
            }
        }
        return targets;
    }

    default Set<T> convertAllIgnoreExceptions(Set<S> sources) {
        Optional<Set<S>> sourcesOpt = ofNullable(sources);
        if (sourcesOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<T> targets = new HashSet<>();
        for (S source : sources) {
            try {
                T target = convert(source);
                targets.add(target);
            } catch (Exception e) {
                log.warn("Element conversion failed: " + source + ". Reason: " + e.getMessage(), e);
            }
        }
        return targets;
    }
}
