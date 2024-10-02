package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.domain.model.Post;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.SETTER,
        uses = {
                SqidsConverterMapper.class,
                CommentMapper.class,
                AccountMapper.class
        }
)
public interface PostMapper {

    @Mapping(target = "comments", qualifiedByName = "CommentWithoutPost")
    @Mapping(source = "id", target = "code", qualifiedByName = "IdToCodeSqids")
    PostData postToPostData(Post post);

    @Named("PostWithoutComments")
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "id", target = "code", qualifiedByName = "IdToCodeSqids")
    PostData postToPostDataWithoutComments(Post post);

    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source = "code", target = "id", qualifiedByName = "CodeSqidsToId")
    Post postDataToPost(PostData postData);

    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source = "code", target = "id", qualifiedByName = "CodeSqidsToId")
    void updatePostFromData(PostData postData, @MappingTarget Post post);
}