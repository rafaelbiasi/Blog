package br.com.rafaelbiasi.blog.model.keygen;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.Parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(ULIDKeyGenerator.class)
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface ULIDKeyIdGenerator {
  Parameter[] parameters() default {};
}