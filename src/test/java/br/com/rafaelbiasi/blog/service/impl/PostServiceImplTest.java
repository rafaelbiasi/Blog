package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.repository.PostRepository;
import br.com.rafaelbiasi.blog.service.PostService;
import br.com.rafaelbiasi.blog.specification.impl.IsNewPostSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

    private PostService postService;
    private AutoCloseable closeable;
    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postService = new PostServiceImpl(postRepository, new IsNewPostSpecification());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findById() {
        //GIVEN
        Post post = Post.builder().id(1L).build();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        //WHEN
        Optional<Post> postResponse = postService.findById(1L);
        //THEN
        Assertions.assertTrue(postResponse.isPresent());
        Assertions.assertEquals(post, postResponse.get());
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
        Assertions.assertEquals(Collections.singletonList(post), postsResponse);
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
        Assertions.assertEquals(post, postsResponse.iterator().next());
        verify(postRepository).findAll(pageable);
    }

    @Test
    void save() {
        //GIVEN
        Post post = Post.builder().title("Título a ser slugificado").build();
        when(postRepository.save(post)).thenReturn(post);
        //WHEN
        Post postResponse = postService.save(post);
        //THEN
        Assertions.assertEquals(post, postResponse);
        Assertions.assertEquals("titulo-a-ser-slugificado", postResponse.getCode());
        verify(postRepository).save(post);
    }

    @Test
    void saveUpdate() {
        //GIVEN
        Post post = Post.builder().code("titulo-slugificado").title("Título a não deve ser slugificado novamente").build();
        when(postRepository.save(post)).thenReturn(post);
        //WHEN
        Post postResponse = postService.save(post);
        //THEN
        Assertions.assertEquals(post, postResponse);
        Assertions.assertEquals("titulo-slugificado", postResponse.getCode());
        verify(postRepository).save(post);
    }

    @Test
    void delete() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        when(postRepository.findByCode("code")).thenReturn(Optional.of(post));
        //WHEN
        postService.delete("code");
        //THEN
        verify(postRepository).findByCode("code");
        verify(postRepository).delete(post);
    }

    @Test
    void deleteCodeNotExists() {
        //GIVEN
        when(postRepository.findByCode("code")).thenReturn(Optional.empty());
        //WHEN
        Executable executable = () -> postService.delete("code");
        //THEN
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals("Post not found", illegalArgumentException.getMessage());
        verify(postRepository).findByCode("code");
    }

    @Test
    void findByCode() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        when(postRepository.findByCode("code")).thenReturn(Optional.of(post));
        //WHEN
        Optional<Post> postResponse = postService.findByCode("code");
        //THEN
        Assertions.assertTrue(postResponse.isPresent());
        Assertions.assertEquals(post, postResponse.get());
        verify(postRepository).findByCode("code");
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}