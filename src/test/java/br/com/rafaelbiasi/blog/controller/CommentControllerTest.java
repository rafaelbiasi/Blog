package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.facade.CommentFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentControllerTest {

    private CommentController commentController;
    @Mock
    private CommentFacade commentFacade;
    @Mock
    private BindingResult result;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private Principal principal;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void add() {
        //GIVEN
        CommentData comment = CommentData.builder().build();
        when(result.hasErrors()).thenReturn(false);
        //WHEN
        commentController.add(comment, result, redirectAttributes, "post-code", principal);
        //THEN
        verify(commentFacade).save(comment, "post-code", principal);
    }

    @Test
    void addHasErrors() {
        //GIVEN
        CommentData comment = CommentData.builder().build();
        when(result.hasErrors()).thenReturn(true);
        //WHEN
        String view = commentController.add(comment, result, redirectAttributes, "post-code", principal);
        //THEN
        assertEquals("redirect:/post/post-code/", view);
        verify(redirectAttributes).addFlashAttribute("comment", comment);
    }

    @Test
    void delete() {
        //GIVEN
        //WHEN
        String view = commentController.delete("code", "post-code");
        //THEN
        assertEquals("redirect:/post/post-code/", view);
        verify(commentFacade).delete("code");
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}