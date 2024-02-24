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
import org.springframework.validation.BindingResult;
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
    @Mock
    private BindingResult bindingResults;

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
    void update() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.of(PostData.builder().code("title-code").build()));
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        Assertions.assertEquals("post_edit", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void updateNotFound() {
        //GIVEN
        when(postFacade.getByCode("title-code")).thenReturn(Optional.empty());
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        Assertions.assertEquals("error404", view);
        verify(postFacade).getByCode("title-code");
    }

    @Test
    void updateSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("filename.png");
        //WHEN
        String view = postController.update(post, bindingResults, model, "code", file);
        //THEN
        Assertions.assertEquals("redirect:/posts/code", view);
        verify(bindingResults).hasErrors();
        verify(postFacade).save(post);
        verify(file).getOriginalFilename();
        verify(fileFacade).save(file);
    }

    @Test
    void updateSaveFileSaveException() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("filename.png");
        doThrow(RuntimeException.class).when(fileFacade).save(file);
        //WHEN
        String view = postController.update(post, bindingResults, model, "code", file);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(bindingResults).hasErrors();
        verify(postFacade).save(post);
        verify(file).getOriginalFilename();
        verify(fileFacade).save(file);
    }

    @Test
    void updateSaveExceptionErro500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(false);
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.update(post, bindingResults, model, "title-code", file);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(bindingResults).hasErrors();
        verify(postFacade).save(post);
    }

    @Test
    void updateSaveHasErrors() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(true);
        //WHEN
        String view = postController.update(post, bindingResults, model, "title-code", file);
        //THEN
        Assertions.assertEquals("post_edit", view);
        verify(bindingResults).hasErrors();
    }

    @Test
    void create() {
        //GIVEN
        //WHEN
        String view = postController.create(model);
        //THEN
        Assertions.assertEquals("post_new", view);
    }

    @Test
    void createSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        AccountData account = AccountData.builder().build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.of(account));
        when(file.getOriginalFilename()).thenReturn("filename.png");
        //WHEN
        String view = postController.create(post, bindingResults, model, file, user);
        //THEN
        Assertions.assertEquals("redirect:/", view);
        verify(bindingResults).hasErrors();
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
        verify(postFacade).save(post);
        verify(file).getOriginalFilename();
        verify(fileFacade).save(file);
    }

    @Test
    void createSaveFileSaveException() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        AccountData account = AccountData.builder().build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.of(account));
        when(file.getOriginalFilename()).thenReturn("filename.png");
        doThrow(RuntimeException.class).when(fileFacade).save(file);
        //WHEN
        String view = postController.create(post, bindingResults, model, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(bindingResults).hasErrors();
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
        verify(postFacade).save(post);
        verify(file).getOriginalFilename();
        verify(fileFacade).save(file);
    }

    @Test
    void createSaveAccountNotFoundError500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.empty());
        //WHEN
        String view = postController.create(post, bindingResults, model, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(bindingResults).hasErrors();
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
    }

    @Test
    void createSaveExceptionError500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        AccountData account = AccountData.builder().build();
        when(bindingResults.hasErrors()).thenReturn(false);
        when(user.getName()).thenReturn("user@domain.com");
        when(accountFacade.findOneByEmail("user@domain.com")).thenReturn(Optional.of(account));
        doThrow(RuntimeException.class).when(postFacade).save(post);
        //WHEN
        String view = postController.create(post, bindingResults, model, file, user);
        //THEN
        Assertions.assertEquals("error500", view);
        verify(bindingResults).hasErrors();
        verify(user).getName();
        verify(accountFacade).findOneByEmail("user@domain.com");
        verify(postFacade).save(post);
    }

    @Test
    void createSaveHasErrors() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResults.hasErrors()).thenReturn(true);
        //WHEN
        String view = postController.create(post, bindingResults, model, file, user);
        //THEN
        Assertions.assertEquals("post_new", view);
        verify(bindingResults).hasErrors();
    }

    @Test
    void delete() {
        //GIVEN
        //WHEN
        String view = postController.delete("code");
        //THEN
        Assertions.assertEquals("redirect:/", view);
        verify(postFacade).delete("code");
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}
