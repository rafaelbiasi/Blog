package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.core.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {
				SqidsConverterMapper.class,
				RoleMapper.class
		}
)
public interface UserMapper {

	@Mapping(target = "password", ignore = true)
	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	UserData toData(User user);

	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	User toModel(UserData userData);

	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	void updateModelFromData(UserData userData, @MappingTarget User user);
}