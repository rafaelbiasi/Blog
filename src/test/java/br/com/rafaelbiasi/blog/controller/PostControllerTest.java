package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import br.com.rafaelbiasi.blog.facade.FileFacade;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PostControllerTest {

    private PostController postController;

    private AutoCloseable closeable;
    @Mock
    private PostFacade postFacade;
    @Mock
    private AccountFacade accountFacade;
    @Mock
    private FileFacade fileFacade;
    @Mock
    private Model model;
    @Mock
    private MultipartFile file;
    @Mock
    private Principal user;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postController = new PostController(postFacade, accountFacade, fileFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void post() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.of(PostData.builder().code("title-code").build()));
        //WHEN
        String view = postController.post("title-code", model);
        //THEN
        Assertions.assertEquals("post", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void postNotFound() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.empty());
        //WHEN
        String view = postController.post("title-code", model);
        //THEN
        Assertions.assertEquals("error404", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void updatePostEdit() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.of(PostData.builder().code("title-code").build()));
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        Assertions.assertEquals("post_edit", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void updatePostEditNotFound() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.empty());
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        Assertions.assertEquals("error404", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void updatePostSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        //WHEN
        String view = postController.update("title-code", post, file);
        //THEN
        Assertions.assertEquals("redirect:/posts/" + "title-code", view);
        verify(postFacade).save(post);
    }

    @Test
    void updatePostSaveExceptionErro500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.update("title-code", post, file);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(postFacade).save(post);
    }

    @Test
    void createNewPost() {
        //GIVEN
        //WHEN
        String view = postController.createNew(model);
        //THEN
        Assertions.assertEquals("post_new", view);
    }

    @Test
    void createNewPostSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        AccountData account = AccountData.builder().build();
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.of(account));
        //WHEN
        String view = postController.createNew(post, file, user);
        //THEN
        Assertions.assertEquals("redirect:/", view);
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
        verify(postFacade).save(post);
    }

    @Test
    void createNewPostSaveAccountNotFoundError500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.empty());
        //WHEN
        String view = postController.createNew(post, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
    }

    @Test
    void createNewPostSaveAccountExceptionError500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        AccountData account = AccountData.builder().build();
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.of(account));
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.createNew(post, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
        verify(postFacade).save(post);
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}
