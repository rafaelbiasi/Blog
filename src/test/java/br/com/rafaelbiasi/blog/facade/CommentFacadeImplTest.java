package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.service.CommentService;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentFacadeImplTest {

    private CommentFacade commentFacade;
    @Mock
    private CommentService commentService;
    @Mock
    private Transformer<CommentData, Comment> commentTransformer;
    @Mock
    private Principal principal;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        commentFacade = new CommentFacadeImpl(commentService, commentTransformer);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        //GIVEN
        CommentData commentData = CommentData.builder().build();
        Comment comment = Comment.builder().build();
        when(principal.getName()).thenReturn("username");
        when(commentTransformer.convert(commentData)).thenReturn(comment);
        //WHEN
        commentFacade.save(commentData, "post-code", principal);
        //THEN
        assertEquals("username", commentData.getAuthor().getUsername());
        assertEquals("post-code", commentData.getPost().getCode());
        verify(commentService).save(comment);
    }

    @Test
    void delete() {
        //GIVEN
        Comment comment = Comment.builder().code("code").build();
        //WHEN
        commentFacade.delete("code");
        //THEN
        verify(commentService).delete(comment);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}