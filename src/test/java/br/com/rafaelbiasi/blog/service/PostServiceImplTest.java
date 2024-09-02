package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private AccountService accountService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postService = new PostServiceImpl(postRepository, accountService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findById() {
        //GIVEN
        Post post = Post.builder().id(1L).build();
        when(postRepository.findById(1L)).thenReturn(of(post));
        //WHEN
        Optional<Post> postResponse = postService.findById(1L);
        //THEN
        assertTrue(postResponse.isPresent());
        assertEquals(post, postResponse.get());
        verify(postRepository).findById(1L);
    }

    @Test
    void findAll() {
        //GIVEN
        Post post = Post.builder().id(1L).build();
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));
        //WHEN
        List<Post> postsResponse = postService.findAll();
        //THEN
        assertEquals(Collections.singletonList(post), postsResponse);
        verify(postRepository).findAll();
    }

    @Test
    void findAllPageable() {
        //GIVEN
        Post post = Post.builder().id(1L).build();
        Pageable pageable = Pageable.ofSize(5);
        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(post)));
        //WHEN
        Page<Post> postsResponse = postService.findAll(pageable);
        //THEN
        assertEquals(post, postsResponse.iterator().next());
        verify(postRepository).findAll(pageable);
    }

    @Test
    void save() {
        //GIVEN
        Account account = Account.builder().username("username").build();
        Post post = Post.builder()
                .title("Title")
                .body("Body")
                .author(account)
                .build();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        when(postRepository.save(post)).thenReturn(post);
        //WHEN
        Post postResponse = postService.save(post);
        //THEN
        assertEquals(post, postResponse);
        verify(accountService).findOneByUsername("username");
        verify(postRepository).save(post);
    }

    @Test
    void saveUpdate() {
        //GIVEN
        Account account = Account.builder().username("username").build();
        Post post = Post.builder()
                .code("titulo-slugificado")
                .title("Título a não deve ser slugificado novamente")
                .author(account)
                .build();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        when(postRepository.save(post)).thenReturn(post);
        //WHEN
        Post postResponse = postService.save(post);
        //THEN
        assertEquals(post, postResponse);
        assertEquals("titulo-slugificado", postResponse.getCode());
        verify(accountService).findOneByUsername("username");
        verify(postRepository).save(post);
    }

    @Test
    void delete() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        //WHEN
        postService.delete(post);
        //THEN
        verify(postRepository).delete(post);
    }

    @Test
    void findByCode() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        when(postRepository.findByCode("code")).thenReturn(of(post));
        //WHEN
        Optional<Post> postResponse = postService.findByCode("code");
        //THEN
        assertTrue(postResponse.isPresent());
        assertEquals(post, postResponse.get());
        verify(postRepository).findByCode("code");
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}