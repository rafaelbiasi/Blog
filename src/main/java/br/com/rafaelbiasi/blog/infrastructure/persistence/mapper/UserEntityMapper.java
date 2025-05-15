package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.domain.model.UserModel;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {
				RoleEntityMapper.class
		}
)
public interface UserEntityMapper {

	UserEntity toEntity(UserModel user);

	@Mapping(target = "posts", ignore = true)
	UserModel toModel(UserEntity userEntity);

}