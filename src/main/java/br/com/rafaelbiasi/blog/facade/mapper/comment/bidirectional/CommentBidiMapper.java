package br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.exception.ConversionException;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CommentBidiMapper implements BidirectionalMapper<Comment, CommentData> {

    private final Transformer<Account, AccountData> accountDataTransformer;
    private final Transformer<AccountData, Account> accountTransformer;
    private final Transformer<PostData, Post> postCodeTransformer;
    private final Transformer<Post, PostData> postDataCodeTransformer;

    public CommentBidiMapper(
            @Lazy @Qualifier("accountDataTransformer") Transformer<Account, AccountData> accountDataTransformer,
            @Lazy @Qualifier("accountTransformer") Transformer<AccountData, Account> accountTransformer,
            @Lazy @Qualifier("postCodeTransformer") Transformer<PostData, Post> postCodeTransformer,
            @Lazy @Qualifier("postDataCodeTransformer") Transformer<Post, PostData> postDataCodeTransformer) {
        this.accountDataTransformer = accountDataTransformer;
        this.accountTransformer = accountTransformer;
        this.postCodeTransformer = postCodeTransformer;
        this.postDataCodeTransformer = postDataCodeTransformer;
    }

    @Override
    public void map(Comment source, CommentData target) throws ConversionException {
        mapGet(source::getText, target::setText);
        mapGet(source::getCreation, target::setCreation);
        mapGet(source::getModified, target::setModified);
        mapGet(source::getPost, postDataCodeTransformer::convert, target::setPost);
        mapGet(source::getAuthor, accountDataTransformer::convert, target::setAuthor);
    }

    @Override
    public void reverseMap(CommentData source, Comment target) throws ConversionException {
        mapGet(source::getText, target::setText);
        mapGet(source::getPost, postCodeTransformer::convert, target::setPost);
        mapGet(source::getAuthor, accountTransformer::convert, target::setAuthor);
    }
}
