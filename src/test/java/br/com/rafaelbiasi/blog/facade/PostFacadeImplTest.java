package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.service.PostServiceImpl;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostFacadeImplTest {

    private PostFacade postFacade;
    @Mock
    private FileFacade fileFacade;
    @Mock
    private PostServiceImpl postService;
    @Mock
    private Transformer<Post, PostData> postDataTransformer;
    @Mock
    private Transformer<PostData, Post> postTransformer;
    @Mock
    private MultipartFile file;
    @Mock
    private Principal principal;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postFacade = new PostFacadeImpl(postService, fileFacade, postDataTransformer, postTransformer);
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
        List<PostData> responsePostDatas = postFacade.findAll();
        //THEN
        assertEquals(postDatas, responsePostDatas);
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
        Page<PostData> responsePostDatas = postFacade.findAll(pageable);
        //THEN
        assertEquals(postDataPage, responsePostDatas);
        verify(postService).findAll(pageable);
        verify(postDataTransformer).convert(post);
    }

    @Test
    void getById() {
        //GIVEN
        int id = 1;
        Post post = Post.builder().code("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findById(id)).thenReturn(of(post));
        when(postDataTransformer.convert(post)).thenReturn(postData);
        //WHEN
        Optional<PostData> respondePostData = postFacade.findById(id);
        //THEN
        assertTrue(respondePostData.isPresent());
        assertEquals(postData, respondePostData.get());
        verify(postService).findById(id);
        verify(postDataTransformer).convert(post);
    }

    @Test
    void saveWithPrincipal() {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        AccountData account = AccountData.builder().build();
        when(principal.getName()).thenReturn("user@domain.com");
        when(postService.findByCode("code")).thenReturn(of(post));
        when(postTransformer.convertTo(postData, post)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, principal);
        //THEN
        assertEquals(account, postData.getAuthor());
        verify(principal).getName();
        verify(postService).findByCode("code");
        verify(postTransformer).convertTo(postData, post);
        verify(postService).save(post);
    }

    @Test
    void saveUpdateWithFile() throws IOException {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findByCode("code")).thenReturn(of(post));
        when(postTransformer.convertTo(postData, post)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, file);
        //THEN
        verify(file).getOriginalFilename();
        verify(postService).findByCode("code");
        verify(postTransformer).convertTo(postData, post);
        verify(postService).save(post);
        verify(fileFacade).save(file);
    }

    @Test
    void saveNewWithFile() throws IOException {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findByCode("code")).thenReturn(empty());
        when(postTransformer.convert(postData)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, file);
        //THEN
        verify(file).getOriginalFilename();
        verify(postService).findByCode("code");
        verify(postTransformer).convert(postData);
        verify(postService).save(post);
    }

    @Test
    void saveUpdateWithFileException() {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findByCode("code")).thenReturn(of(post));
        when(postTransformer.convertTo(postData, post)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        doThrow(new RuntimeException()).when(fileFacade).save(file);
        //WHEN
        Executable executable = () -> postFacade.save(postData, file);
        //THEN
        assertThrows(RuntimeException.class, executable);
        verify(file).getOriginalFilename();
        verify(postService).findByCode("code");
        verify(postTransformer).convertTo(postData, post);
        verify(postService).save(post);
        verify(fileFacade).save(file);
    }

    @Test
    void saveUpdateWithFileAndPrincipal() throws IOException {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().code("code").build();
        when(principal.getName()).thenReturn("user@domain.com");
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findByCode("code")).thenReturn(of(post));
        when(postTransformer.convertTo(postData, post)).thenReturn(post);
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, file, principal);
        //THEN
        verify(principal).getName();
        verify(file).getOriginalFilename();
        verify(postService).findByCode("code");
        verify(postTransformer).convertTo(postData, post);
        verify(postService).save(post);
        verify(fileFacade).save(file);
    }

    @Test
    void delete() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        when(postService.findByCode("code")).thenReturn(of(post));
        //WHEN
        postFacade.delete("code");
        //THEN
        verify(postService).delete(post);
    }

    @Test
    void deletePostNotFound() {
        //GIVEN
        when(postService.findByCode("code")).thenReturn(empty());
        //WHEN
        boolean deleted = postFacade.delete("code");
        //THEN
        assertFalse(deleted);
    }

    @Test
    void getByCode() {
        //GIVEN
        Post post = Post.builder().code("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findByCode("code")).thenReturn(of(post));
        when(postDataTransformer.convert(post)).thenReturn(postData);
        //WHEN
        Optional<PostData> responsePostData = postFacade.findByCode("code");
        //THEN
        assertTrue(responsePostData.isPresent());
        assertEquals(postData, responsePostData.get());
        verify(postService).findByCode("code");
        verify(postDataTransformer).convert(post);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}