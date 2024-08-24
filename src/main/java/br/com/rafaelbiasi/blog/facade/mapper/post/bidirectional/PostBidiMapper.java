package br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PostBidiMapper implements BidirectionalMapper<Post, PostData> {

    private final Transformer<Account, AccountData> accountDataTransformer;
    private final Transformer<AccountData, Account> accountTransformer;
    private final Transformer<Comment, CommentData> commentDataTransformer;

    public PostBidiMapper(
            @Lazy @Qualifier("accountDataTransformer") Transformer<Account, AccountData> accountDataTransformer,
            @Lazy @Qualifier("accountTransformer") Transformer<AccountData, Account> accountTransformer,
            @Lazy @Qualifier("commentDataTransformer") Transformer<Comment, CommentData> commentDataTransformer) {
        this.accountDataTransformer = accountDataTransformer;
        this.accountTransformer = accountTransformer;
        this.commentDataTransformer = commentDataTransformer;
    }

    @Override
    public void map(Post source, PostData target) throws ConversionException {
        mapGet(source::getModified, target::setModified);
        mapGet(source::getCreation, target::setCreation);
        mapGet(source::getBody, target::setBody);
        mapGet(source::getTitle, target::setTitle);
        mapGet(source::getImageFilePath, target::setImageFilePath, () -> target.setImageFilePath(""));
        mapGet(source::getAuthor, accountDataTransformer::convert, target::setAuthor);
        mapGet(source::getComments, commentDataTransformer::convertAll, target::setComments);
    }

    @Override
    public void reverseMap(PostData source, Post target) throws ConversionException {
        mapGet(source::getBody, target::setBody);
        mapGet(source::getTitle, target::setTitle);
        mapGet(source::getImageFilePath, target::setImageFilePath);
        mapGet(source::getAuthor, accountTransformer::convert, target::setAuthor);
    }
}
