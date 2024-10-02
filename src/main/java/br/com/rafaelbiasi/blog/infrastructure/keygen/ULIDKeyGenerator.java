package br.com.rafaelbiasi.blog.infrastructure.keygen;

import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ULIDKeyGenerator implements IdentifierGenerator {

    @Override
    public Object generate(
            final SharedSessionContractImplementor session,
            final Object object
    ) throws HibernateException {
        return UlidCreator.getUlid().toString();
    }
}