package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.domain.entity.Comment;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.SETTER,
        uses = {
                SqidsConverterMapper.class,
                PostMapper.class,
                AccountMapper.class
        }
)
public interface CommentMapper {

    @Mapping(source = "id", target = "code", qualifiedByName = "IdToCodeSqids")
    CommentData commentToCommentData(Comment comment);

    @Named("CommentWithoutPost")
    @Mapping(target = "post", ignore = true)
    @Mapping(source = "id", target = "code", qualifiedByName = "IdToCodeSqids")
    CommentData commentToCommentDataWithoutPost(Comment comment);

    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source = "code", target = "id", qualifiedByName = "CodeSqidsToId")
    Comment commentDataToComment(CommentData commentData);

}