package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.SETTER
)
public interface SqidsConverterMapper {

    @Named("idToSqidsCode")
    default String idToSqidsCode(Long id) {
        return SqidsUtil.encodeId(id);
    }

    @Named("sqidsCodeToId")
    default Long sqidsCodeToId(String code) {
        return SqidsUtil.decodeId(code);
    }
}
