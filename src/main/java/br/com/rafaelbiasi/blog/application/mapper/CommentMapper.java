package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.core.model.Comment;
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
	CommentData toData(Comment comment);

	@Named("CommentWithoutPost")
	@Mapping(target = "post", ignore = true)
	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	CommentData toDataWithoutPost(Comment comment);

	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	Comment toModel(CommentData commentData);

}