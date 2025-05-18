package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.model.Role;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER
)
public interface RoleEntityMapper {

	RoleEntity toEntity(Role role);

	Role toModel(RoleEntity roleEntity);
}