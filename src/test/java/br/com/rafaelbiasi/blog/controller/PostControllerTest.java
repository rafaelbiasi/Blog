package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundException;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import br.com.rafaelbiasi.blog.ui.controller.PostController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        String view = postController.post("code", "title-code", model, request);
        //THEN
        assertEquals("post/post", view);
        verify(postFacade).findByCode("title-code");
    }

    @Test
    void postNotFound() {
        //GIVEN
        when(postFacade.findByCode("title-code")).thenReturn(empty());
        //WHEN
        Executable executable = () -> postController.post("code", "title-code", model, request);
        //THEN
        assertThrows(ResourceNotFoundException.class, executable);
        verify(postFacade).findByCode("title-code");
    }

//    @Test
//    void edit() {
//        //GIVEN
//        when(postFacade.findByCode("title-code")).thenReturn(of(PostData.builder().code("title-code").build()));
//        //WHEN
//        String view = postController.edit("title-code", model);
//        //THEN
//        assertEquals("post/form", view);
//        verify(postFacade).findByCode("title-code");
//    }
//
//    @Test
//    void editNotFound() {
//        //GIVEN
//        when(postFacade.findByCode("title-code")).thenReturn(empty());
//        //WHEN
//        Executable executable = () -> postController.edit("title-code", model);
//        //THEN
//        assertThrows(ResourceNotFoundException.class, executable);
//        verify(postFacade).findByCode("title-code");
//    }
//
//    @Test
//    void create() {
//        //GIVEN
//        //WHEN
//        String view = postController.create(model);
//        //THEN
//        assertEquals("post/form", view);
//    }
//
//    @Test
//    void save() {
//        //GIVEN
//        PostData post = PostData.builder().code("title-code").build();
//        when(postFacade.save(post)).thenReturn(post);
//        when(bindingResult.hasErrors()).thenReturn(false);
//        //WHEN
//        String view = postController.save(post, bindingResult, model, null, principal);
//        //THEN
//        assertEquals("redirect:/post/title-code/", view);
//        verify(bindingResult).hasErrors();
//        verify(postFacade).save(post);
//    }
//
//    @Test
//    void saveWithFile() {
//        //GIVEN
//        PostData post = PostData.builder().code("title-code").build();
//        when(postFacade.save(post, file)).thenReturn(post);
//        when(bindingResult.hasErrors()).thenReturn(false);
//        //WHEN
//        String view = postController.save(post, bindingResult, model, file, principal);
//        //THEN
//        assertEquals("redirect:/post/title-code/", view);
//        verify(bindingResult).hasErrors();
//        verify(postFacade).save(post, file);
//    }
//
//    @Test
//    void saveExceptionError500() {
//        //GIVEN
//        PostData post = PostData.builder().code("title-code").build();
//        when(bindingResult.hasErrors()).thenReturn(false);
//        doThrow(RuntimeException.class).when(postFacade).save(post, file);
//        //WHEN
//        Executable executable = () -> postController.save(post, bindingResult, model, file, principal);
//        //THEN
//        assertThrows(RuntimeException.class, executable);
//        verify(bindingResult).hasErrors();
//        verify(postFacade).save(post, file);
//    }
//
//    @Test
//    void saveHasErrors() {
//        //GIVEN
//        PostData post = PostData.builder().code("title-code").build();
//        when(bindingResult.hasErrors()).thenReturn(true);
//        //WHEN
//        String view = postController.save(post, bindingResult, model, file, principal);
//        //THEN
//        assertEquals("post/form", view);
//        verify(bindingResult).hasErrors();
//    }
//
//    @Test
//    void delete() {
//        //GIVEN
//        when(postFacade.delete("title-code")).thenReturn(true);
//        //WHEN
//        String view = postController.delete("title-code");
//        //THEN
//        assertEquals("redirect:/", view);
//        verify(postFacade).delete("title-code");
//    }
//
//    @Test
//    void deletePostNotFound() {
//        //GIVEN
//        when(postFacade.delete("title-code")).thenReturn(false);
//        //WHEN
//        Executable executable = () -> postController.delete("title-code");
//        //THEN
//        assertThrows(ResourceNotFoundException.class, executable);
//        verify(postFacade).delete("title-code");
//    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}
