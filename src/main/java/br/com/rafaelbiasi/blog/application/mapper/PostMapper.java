package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;
import org.mapstruct.*;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER,
		uses = {
				SqidsConverterMapper.class,
				CommentMapper.class,
				UserMapper.class
		}
)
public interface PostMapper {

	@Mapping(target = "comments", qualifiedByName = "CommentWithoutPost")
	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	PostData toData(PostModel post);

	@Named("PostWithoutComments")
	@Mapping(target = "comments", ignore = true)
	@Mapping(source = "id", target = "code", qualifiedByName = "idToSqidsCode")
	PostData toDataWithoutComments(PostModel post);

	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	PostModel toModel(PostData postData);

	@Mapping(target = "creation", ignore = true)
	@Mapping(target = "modified", ignore = true)
	@Mapping(source = "code", target = "id", qualifiedByName = "sqidsCodeToId")
	void updateModelFromData(PostData postData, @MappingTarget PostModel post);
}