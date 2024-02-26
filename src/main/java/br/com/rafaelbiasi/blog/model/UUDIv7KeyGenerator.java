package br.com.rafaelbiasi.blog.model;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UUDIv7KeyGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UuidCreator.getTimeOrderedEpoch().toString().replace("-", "").toUpperCase();
    }
}