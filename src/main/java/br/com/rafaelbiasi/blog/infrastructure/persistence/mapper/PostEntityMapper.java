package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.domain.model.PostModel;
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

	PostEntity toEntity(PostModel post);

	@Mapping(target = "comments", qualifiedByName = "CommentWithoutPost")
	PostModel toModel(PostEntity postEntity);

	@Named("PostWithoutComments")
	@Mapping(target = "comments", ignore = true)
	PostModel toModelWithoutComments(PostEntity post);

}