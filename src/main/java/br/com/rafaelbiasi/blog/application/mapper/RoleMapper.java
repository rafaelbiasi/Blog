package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.core.domain.model.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        uses = {SqidsConverterMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.SETTER
)
public interface RoleMapper {

    @Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
    RoleData toData(Role role);

    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
    Role toModel(RoleData roleData);
}