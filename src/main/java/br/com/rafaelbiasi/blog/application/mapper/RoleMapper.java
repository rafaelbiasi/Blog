package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.domain.entity.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.SETTER
)
public interface RoleMapper {

    @Mapping(source = "id", target = "code")
    RoleData roleToRoleData(Role role);

    @Mapping(source = "code", target = "id")
    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Role roleDataToRole(RoleData roleData);
}