package br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountBidiMapper implements BidirectionalMapper<Account, AccountData> {

    private final Transformer<RoleData, Role> roleTransformer;
    private final Transformer<PostData, Post> postTransformer;
    private final Transformer<Role, RoleData> roleDataTransformer;
    private final Transformer<Post, PostData> postDataCodeTransformer;

    public AccountBidiMapper(
            @Lazy @Qualifier("roleTransformer") Transformer<RoleData, Role> roleTransformer,
            @Lazy @Qualifier("postTransformer") Transformer<PostData, Post> postTransformer,
            @Lazy @Qualifier("roleDataTransformer") Transformer<Role, RoleData> roleDataTransformer,
            @Lazy @Qualifier("postDataCodeTransformer") Transformer<Post, PostData> postDataCodeTransformer) {
        this.roleTransformer = roleTransformer;
        this.postTransformer = postTransformer;
        this.roleDataTransformer = roleDataTransformer;
        this.postDataCodeTransformer = postDataCodeTransformer;
    }

    @Override
    public void map(Account source, AccountData target) throws ConversionException {
        mapGet(source::getEmail, target::setEmail);
        mapGet(source::getUsername, target::setUsername);
        mapGet(source::getFirstName, target::setFirstName);
        mapGet(source::getLastName, target::setLastName);
        mapGet(source::getCreation, target::setCreation);
        mapGet(source::getModified, target::setModified);
        mapGet(source::getRoles, roleDataTransformer::convertAll, target::setRoles);
        mapGet(source::getPosts, postDataCodeTransformer::convertAll, target::setPosts);
    }

    @Override
    public void reverseMap(AccountData source, Account target) throws ConversionException {
        mapGet(source::getEmail, target::setEmail);
        mapGet(source::getUsername, target::setUsername);
        mapGet(source::getFirstName, target::setFirstName);
        mapGet(source::getLastName, target::setLastName);
        mapGet(source::getPassword, target::setPassword);
        mapGet(source::getRoles, roleTransformer::convertAll, target::setRoles);
        mapGet(source::getPosts, postTransformer::convertAll, target::setPosts);
    }
}
