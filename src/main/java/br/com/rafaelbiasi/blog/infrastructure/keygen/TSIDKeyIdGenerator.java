package br.com.rafaelbiasi.blog.infrastructure.keygen;

import br.com.rafaelbiasi.blog.infrastructure.keygen.impl.TSIDKeyGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(TSIDKeyGenerator.class)
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface TSIDKeyIdGenerator {
}