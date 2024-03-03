package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostControllerTest {

    private PostController postController;
    @Mock
    private PostFacade postFacade;
    @Mock
    private Model model;
    @Mock
    private MultipartFile file;
    @Mock
    private Principal principal;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private HttpServletRequest request;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postController = new PostController(postFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void post() {
        //GIVEN
        when(postFacade.findByCode("title-code")).thenReturn(of(PostData.builder().code("title-code").build()));
        //WHEN
        String view = postController.post("title-code", model, request);
        //THEN
        assertEquals("post", view);
        verify(postFacade).findByCode("title-code");
    }

    @Test
    void postNotFound() {
        //GIVEN
        when(postFacade.findByCode("title-code")).thenReturn(empty());
        //WHEN
        String view = postController.post("title-code", model, request);
        //THEN
        assertEquals("error404", view);
        verify(postFacade).findByCode("title-code");
    }

    @Test
    void update() {
        //GIVEN
        when(postFacade.findByCode("title-code")).thenReturn(of(PostData.builder().code("title-code").build()));
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        assertEquals("post_edit", view);
        verify(postFacade).findByCode("title-code");
    }

    @Test
    void updateNotFound() {
        //GIVEN
        when(postFacade.findByCode("title-code")).thenReturn(empty());
        //WHEN
        String view = postController.update("title-code", model);
        //THEN
        assertEquals("error404", view);
        verify(postFacade).findByCode("title-code");
    }

    @Test
    void updateSaveWithFile() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        String view = postController.update(post, bindingResult, model, "title-code", null);
        //THEN
        assertEquals("redirect:/post/title-code", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post);
    }

    @Test
    void updateSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        String view = postController.update(post, bindingResult, model, "title-code", file);
        //THEN
        assertEquals("redirect:/post/title-code", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, file);
    }

    @Test
    void updateSaveFileSaveException() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException()).when(postFacade).save(post, file);
        //WHEN
        String view = postController.update(post, bindingResult, model, "title-code", file);
        //THEN
        assertEquals("error500", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, file);
    }

    @Test
    void updateSaveExceptionErro500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(RuntimeException.class).when(postFacade).save(post, file);
        //WHEN
        String view = postController.update(post, bindingResult, model, "title-code", file);
        //THEN
        assertEquals("error500", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, file);
    }

    @Test
    void updateSaveHasErrors() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String view = postController.update(post, bindingResult, model, "title-code", file);
        //THEN
        assertEquals("post_edit", view);
        verify(bindingResult).hasErrors();
    }

    @Test
    void create() {
        //GIVEN
        //WHEN
        String view = postController.create(model);
        //THEN
        assertEquals("post_new", view);
    }

    @Test
    void createSave() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        String view = postController.create(post, bindingResult, model, null, principal);
        //THEN
        assertEquals("redirect:/", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, principal);
    }

    @Test
    void createSaveWithFile() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        String view = postController.create(post, bindingResult, model, file, principal);
        //THEN
        assertEquals("redirect:/", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, file, principal);
    }

    @Test
    void createSaveExceptionError500() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(RuntimeException.class).when(postFacade).save(post, file, principal);
        //WHEN
        String view = postController.create(post, bindingResult, model, file, principal);
        //THEN
        assertEquals("error500", view);
        verify(bindingResult).hasErrors();
        verify(postFacade).save(post, file, principal);
    }

    @Test
    void createSaveHasErrors() {
        //GIVEN
        PostData post = PostData.builder().code("title-code").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String view = postController.create(post, bindingResult, model, file, principal);
        //THEN
        assertEquals("post_new", view);
        verify(bindingResult).hasErrors();
    }

    @Test
    void delete() {
        //GIVEN
        //WHEN
        String view = postController.delete("title-code");
        //THEN
        assertEquals("redirect:/", view);
        verify(postFacade).delete("title-code");
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}
