package br.com.rafaelbiasi.blog.infrastructure.keygen;

import br.com.rafaelbiasi.blog.domain.model.ItemEntity;
import com.github.f4b6a3.tsid.TsidFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TSIDKeyGenerator implements IdentifierGenerator {

    private static final Map<Integer, TsidFactory> TSID_FACTORY_MAP = new HashMap<>();

    public static long extractTypeCode(long tsid) {
        return (tsid >> 10) & 0xFFF;
    }

    @Override
    public Object generate(
            final SharedSessionContractImplementor session,
            final Object object
    ) throws HibernateException {
        int entityType = object instanceof ItemEntity itemEntity ? itemEntity.getTypeCode() : 0;
        return TSID_FACTORY_MAP.computeIfAbsent(
                        entityType,
                        TsidFactory::newInstance4096
                )
                .create()
                .toLong();
    }
}