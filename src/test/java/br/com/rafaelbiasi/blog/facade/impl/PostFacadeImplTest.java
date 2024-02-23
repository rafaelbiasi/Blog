package br.com.rafaelbiasi.blog.facade.impl;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.service.impl.PostServiceImpl;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostFacadeImplTest {

    private PostFacade postFacade;
    private AutoCloseable closeable;
    @Mock
    private PostServiceImpl postService;
    @Mock
    private Transformer<Post, PostData> postDataTransformer;
    @Mock
    private Transformer<PostData, Post> postTransformer;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postFacade = new PostFacadeImpl(postService, postDataTransformer, postTransformer);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAll() {
        //GIVEN
        List<Post> posts = Collections.singletonList(Post.builder().code("code").build());
        List<PostData> postDatas = Collections.singletonList(PostData.builder().code("code").build());
        when(postService.findAll()).thenReturn(posts);
        when(postDataTransformer.convertAll(posts)).thenReturn(postDatas);
        //WHEN
        List<PostData> responsePostDatas = postFacade.getAll();
        //THEN
        Assertions.assertEquals(postDatas, responsePostDatas);
        verify(postService).findAll();
        verify(postDataTransformer).convertAll(posts);
    }

    @Test
    void getAllPageable() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        PostData postData = PostData.builder().code("code").build();
        List<Post> posts = Collections.singletonList(post);
        List<PostData> postDatas = Collections.singletonList(postData);
        Pageable pageable = Pageable.ofSize(10);
        Page<Post> postPage = new PageImpl<>(posts);
        Page<PostData> postDataPage = new PageImpl<>(postDatas);
        when(postService.findAll(pageable)).thenReturn(postPage);
        when(postDataTransformer.convert(post)).thenReturn(postData);
        //WHEN
        Page<PostData> responsePostDatas = postFacade.getAll(pageable);
        //THEN
        Assertions.assertEquals(postDataPage, responsePostDatas);
        verify(postService).findAll(pageable);
        verify(postDataTransformer).convert(post);
    }

    @Test
    void getById() {
        //GIVEN
        int id = 1;
        Post post = Post.builder().code("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findById(id)).thenReturn(Optional.of(post));
        when(postDataTransformer.convert(post)).thenReturn(postData);
        //WHEN
        Optional<PostData> respondePostData = postFacade.getById(id);
        //THEN
        Assertions.assertTrue(respondePostData.isPresent());
        Assertions.assertEquals(postData, respondePostData.get());
        verify(postService).findById(id);
        verify(postDataTransformer).convert(post);
    }

    @Test
    void saveUpdate() {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(postService.findByCode("code")).thenReturn(Optional.of(post));
        when(postTransformer.convertTo(postData, post)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData);
        //THEN
        verify(postService).findByCode("code");
        verify(postTransformer).convertTo(postData, post);
        verify(postService).save(post);
    }

    @Test
    void saveNew() {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(postService.findByCode("code")).thenReturn(Optional.empty());
        when(postTransformer.convert(postData)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData);
        //THEN
        verify(postService).findByCode("code");
        verify(postTransformer).convert(postData);
        verify(postService).save(post);
    }

    @Test
    void delete() {
        //GIVEN
        //WHEN
        postFacade.delete("code");
        //THEN
        verify(postService).delete("code");
    }

    @Test
    void getByCode() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findByCode("code")).thenReturn(Optional.of(post));
        when(postDataTransformer.convert(post)).thenReturn(postData);
        //WHEN
        Optional<PostData> responsePostData = postFacade.getByCode("code");
        //THEN
        Assertions.assertTrue(responsePostData.isPresent());
        Assertions.assertEquals(postData, responsePostData.get());
        verify(postService).findByCode("code");
        verify(postDataTransformer).convert(post);
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}