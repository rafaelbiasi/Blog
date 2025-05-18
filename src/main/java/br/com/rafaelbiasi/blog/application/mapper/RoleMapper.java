package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.core.model.Role;
import org.mapstruct.*;

@Mapper(
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER,
		uses = {SqidsConverterMapper.class}
)
public interface RoleMapper {

	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	RoleData toData(Role role);

	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	Role toModel(RoleData roleData);
}