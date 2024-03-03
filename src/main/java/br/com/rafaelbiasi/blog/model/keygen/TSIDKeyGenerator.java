package br.com.rafaelbiasi.blog.model.keygen;

import com.github.f4b6a3.tsid.TsidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TSIDKeyGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return TsidCreator.getTsid().toLong();
    }
}