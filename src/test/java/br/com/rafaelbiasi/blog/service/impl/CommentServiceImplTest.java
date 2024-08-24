package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.repository.CommentRepository;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.service.CommentService;
import br.com.rafaelbiasi.blog.service.CommentServiceImpl;
import br.com.rafaelbiasi.blog.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentServiceImplTest {

    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private PostService postService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, accountService, postService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findById() {
        //GIVEN
        Comment comment = Comment.builder().id(1L).build();
        when(commentRepository.findById(1L)).thenReturn(of(comment));
        //WHEN
        Optional<Comment> commentResponse = commentService.findById(1L);
        //THEN
        assertTrue(commentResponse.isPresent());
        assertEquals(comment, commentResponse.get());
        verify(commentRepository).findById(1L);
    }

    @Test
    void findByCode() {
        //GIVEN
        Comment comment = Comment.builder().id(1L).code("code").build();
        when(commentRepository.findByCode("code")).thenReturn(of(comment));
        //WHEN
        Optional<Comment> commentResponse = commentService.findByCode("code");
        //THEN
        assertTrue(commentResponse.isPresent());
        assertEquals(comment, commentResponse.get());
        verify(commentRepository).findByCode("code");
    }

    @Test
    void delete() {
        //GIVEN
        Comment comment = Comment.builder().id(1L).build();
        //WHEN
        commentService.delete(comment);
        //THEN
        verify(commentRepository).delete(comment);
    }

    @Test
    void save() {
        //GIVEN
        Account accountToFind = Account.builder()
                .username("username"
                ).build();
        Post postToFind = Post.builder().code("code").build();
        Comment comment = Comment.builder()
                .id(1L)
                .author(accountToFind)
                .post(postToFind)
                .build();
        Account account = Account.builder().id(1L).username("username").build();
        Post post = Post.builder().id(1L).code("code").build();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        when(postService.findByCode("code")).thenReturn(of(post));
        //WHEN
        commentService.save(comment);
        //THEN
        assertEquals(account, comment.getAuthor());
        assertEquals(post, comment.getPost());
        assertNotEquals(accountToFind, comment.getAuthor());
        assertNotEquals(postToFind, comment.getPost());
        verify(accountService).findOneByUsername("username");
        verify(postService).findByCode("code");
        verify(commentRepository).save(comment);
    }

    @Test
    void savePostNotFound() {
        //GIVEN
        Account accountToFind = Account.builder()
                .username("username"
                ).build();
        Post postToFind = Post.builder().code("code").build();
        Comment comment = Comment.builder()
                .id(1L)
                .author(accountToFind)
                .post(postToFind)
                .build();
        Account account = Account.builder().id(1L).username("username").build();
        Post post = Post.builder().id(1L).code("code").build();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        when(postService.findByCode("code")).thenReturn(empty());
        //WHEN
        Executable executable = () -> commentService.save(comment);
        //THEN
        assertThrows(IllegalArgumentException.class, executable);
        assertEquals(account, comment.getAuthor());
        assertNotEquals(post, comment.getPost());
        assertNotEquals(accountToFind, comment.getAuthor());
        assertEquals(postToFind, comment.getPost());
        verify(accountService).findOneByUsername("username");
        verify(postService).findByCode("code");
    }

    @Test
    void saveAccountNotFound() {
        //GIVEN
        Account accountToFind = Account.builder()
                .username("username"
                ).build();
        Post postToFind = Post.builder().code("code").build();
        Comment comment = Comment.builder()
                .id(1L)
                .author(accountToFind)
                .post(postToFind)
                .build();
        Account account = Account.builder().id(1L).username("username").build();
        Post post = Post.builder().id(1L).code("code").build();
        when(accountService.findOneByUsername("username")).thenReturn(empty());
        //WHEN
        Executable executable = () -> commentService.save(comment);
        //THEN
        assertThrows(IllegalArgumentException.class, executable);
        assertNotEquals(account, comment.getAuthor());
        assertNotEquals(post, comment.getPost());
        assertEquals(accountToFind, comment.getAuthor());
        assertEquals(postToFind, comment.getPost());
        verify(accountService).findOneByUsername("username");
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}