package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.model.Post;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.PostEntity;
import org.mapstruct.*;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER,
		uses = {
				CommentEntityMapper.class,
				UserEntityMapper.class
		}
)
public interface PostEntityMapper {

	PostEntity toEntity(Post post);

	@Mapping(target = "comments", qualifiedByName = "CommentWithoutPost")
	Post toModel(PostEntity postEntity);

	@Named("PostWithoutComments")
	@Mapping(target = "comments", ignore = true)
	Post toModelWithoutComments(PostEntity post);

}