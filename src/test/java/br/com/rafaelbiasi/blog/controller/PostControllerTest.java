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
        String code = "title-code";
        when(postFacade.getByCode(code)).thenReturn(Optional.of(PostData.builder().code(code).build()));
        //WHEN
        String view = postController.post(code, model);
        //THEN
        Assertions.assertEquals("post", view);
    }

    @Test
    void postNotFound() {
        //GIVEN
        String code = "title-code";
        when(postFacade.getByCode(code)).thenReturn(Optional.empty());
        //WHEN
        String view = postController.post(code, model);
        //THEN
        Assertions.assertEquals("error404", view);
    }

    @Test
    void updatePostEdit() {
        //GIVEN
        String code = "title-code";
        when(postFacade.getByCode(code)).thenReturn(Optional.of(PostData.builder().code(code).build()));
        //WHEN
        String view = postController.updatePost(code, model);
        //THEN
        Assertions.assertEquals("post_edit", view);
    }

    @Test
    void updatePostEditNotFound() {
        //GIVEN
        String code = "title-code";
        when(postFacade.getByCode(code)).thenReturn(Optional.empty());
        //WHEN
        String view = postController.updatePost(code, model);
        //THEN
        Assertions.assertEquals("error404", view);
    }

    @Test
    void updatePostSave() {
        //GIVEN
        String code = "title-code";
        PostData post = PostData.builder().code(code).build();
        doNothing().when(postFacade).save(post);
        //WHEN
        String view = postController.updatePost(code, post, file);
        //THEN
        Assertions.assertEquals("redirect:/posts/" + code, view);
    }

    @Test
    void updatePostSaveExceptionErro500() {
        //GIVEN
        String code = "title-code";
        PostData post = PostData.builder().code(code).build();
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.updatePost(code, post, file);
        //THEN
        Assertions.assertEquals("error500", view);
    }

    @Test
    void createNewPost() {
        //GIVEN
        //WHEN
        String view = postController.createNewPost(model);
        //THEN
        Assertions.assertEquals("post_new", view);
    }

    @Test
    void createNewPostSave() {
        //GIVEN
        String code = "title-code";
        String email = "user@domain.com";
        PostData post = PostData.builder().code(code).build();
        AccountData account = AccountData.builder().build();
        when(user.getName()).thenReturn(email);
        when(accountFacade.findOneByEmail(email)).thenReturn(Optional.of(account));
        doNothing().when(postFacade).save(post);
        //WHEN
        String view = postController.createNewPost(post, file, user);
        //THEN
        Assertions.assertEquals("redirect:/", view);
    }

    @Test
    void createNewPostSaveAccountNotFoundError500() {
        //GIVEN
        String code = "title-code";
        String email = "user@domain.com";
        PostData post = PostData.builder().code(code).build();
        when(user.getName()).thenReturn(email);
        when(accountFacade.findOneByEmail(email)).thenReturn(Optional.empty());
        doNothing().when(postFacade).save(post);
        //WHEN
        String view = postController.createNewPost(post, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
    }

    @Test
    void createNewPostSaveAccountExceptionError500() {
        //GIVEN
        String code = "title-code";
        String email = "user@domain.com";
        PostData post = PostData.builder().code(code).build();
        AccountData account = AccountData.builder().build();
        when(user.getName()).thenReturn(email);
        when(accountFacade.findOneByEmail(email)).thenReturn(Optional.of(account));
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.createNewPost(post, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
    }
}