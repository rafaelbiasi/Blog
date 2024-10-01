package br.com.rafaelbiasi.keygen;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(UUDIv7KeyGenerator.class)
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface UUDIv7KeyIdGenerator {
}