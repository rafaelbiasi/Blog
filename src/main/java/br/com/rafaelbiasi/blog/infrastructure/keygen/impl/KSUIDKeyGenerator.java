package br.com.rafaelbiasi.blog.infrastructure.keygen.impl;

import com.github.f4b6a3.ksuid.KsuidCreator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@Slf4j
public class KSUIDKeyGenerator implements IdentifierGenerator {

	@Override
	public Object generate(
			final SharedSessionContractImplementor session,
			final Object object
	) throws HibernateException {
		return KsuidCreator.getKsuid().toString();
	}
}