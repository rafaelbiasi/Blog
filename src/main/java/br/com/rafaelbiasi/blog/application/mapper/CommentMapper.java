package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
import org.mapstruct.*;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER,
		uses = {
				SqidsConverterMapper.class,
				PostMapper.class,
				UserMapper.class
		}
)
public interface CommentMapper {

	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	CommentData toData(CommentModel comment);

	@Named("CommentWithoutPost")
	@Mapping(target = "post", ignore = true)
	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	CommentData toDataWithoutPost(CommentModel comment);

	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	CommentModel toModel(CommentData commentData);

}