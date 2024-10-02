package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import br.com.rafaelbiasi.blog.application.facade.impl.PostFacadeImpl;
import br.com.rafaelbiasi.blog.application.mapper.PostMapper;
import br.com.rafaelbiasi.blog.domain.model.Post;
import br.com.rafaelbiasi.blog.domain.service.impl.PostServiceImpl;
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
    private MultipartFile file;
    @Mock
    private Principal principal;
    @Mock
    private PostMapper postMapper;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        postFacade = new PostFacadeImpl(postService, fileFacade, postMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAll() {
        //GIVEN
        List<Post> posts = Collections.singletonList(Post.builder().slugifiedTitle("code").build());
        List<PostData> postDatas = Collections.singletonList(PostData.builder().code("code").build());
        when(postService.findAll()).thenReturn(posts);
        //WHEN
        List<PostData> responsePostDatas = postFacade.findAll();
        //THEN
        assertEquals(postDatas, responsePostDatas);
        verify(postService).findAll();
    }

    @Test
    void getAllPageable() {
        //GIVEN
        Post post = Post.builder().slugifiedTitle("code").build();
        PostData postData = PostData.builder().code("code").build();
        List<Post> posts = Collections.singletonList(post);
        List<PostData> postDatas = Collections.singletonList(postData);
        Pageable pageable = Pageable.ofSize(10);
        Page<Post> postPage = new PageImpl<>(posts);
        Page<PostData> postDataPage = new PageImpl<>(postDatas);
        when(postService.findAll(pageable)).thenReturn(postPage);
        //WHEN
        Page<PostData> responsePostDatas = postFacade.findAll(pageable);
        //THEN
        assertEquals(postDataPage, responsePostDatas);
        verify(postService).findAll(pageable);
    }

    @Test
    void getById() {
        //GIVEN
        int id = 1;
        Post post = Post.builder().slugifiedTitle("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findById(id)).thenReturn(of(post));
        //WHEN
        Optional<PostData> respondePostData = postFacade.findById(id);
        //THEN
        assertTrue(respondePostData.isPresent());
        assertEquals(postData, respondePostData.get());
        verify(postService).findById(id);
    }

    @Test
    void saveUpdateWithFile() throws IOException {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().slugifiedTitle("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findById(1)).thenReturn(of(post));
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, file);
        //THEN
        verify(file).getOriginalFilename();
        verify(postService).findById(1);
        verify(postService).save(post);
        verify(fileFacade).save(file);
    }

    @Test
    void saveNewWithFile() throws IOException {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().slugifiedTitle("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findById(1)).thenReturn(empty());
        when(postService.save(post)).thenReturn(post);
        //WHEN
        postFacade.save(postData, file);
        //THEN
        verify(file).getOriginalFilename();
        verify(postService).findById(1);
        verify(postService).save(post);
    }

    @Test
    void saveUpdateWithFileException() {
        //GIVEN
        PostData postData = PostData.builder().code("code").build();
        Post post = Post.builder().slugifiedTitle("code").build();
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(postService.findById(1)).thenReturn(of(post));
        when(postService.save(post)).thenReturn(post);
        doThrow(new RuntimeException()).when(fileFacade).save(file);
        //WHEN
        Executable executable = () -> postFacade.save(postData, file);
        //THEN
        assertThrows(RuntimeException.class, executable);
        verify(file).getOriginalFilename();
        verify(postService).findById(1);
        verify(postService).save(post);
        verify(fileFacade).save(file);
    }

    @Test
    void delete() {
        //GIVEN
        Post post = Post.builder().slugifiedTitle("code").build();
        when(postService.findById(1)).thenReturn(of(post));
        //WHEN
        postFacade.delete("code");
        //THEN
        verify(postService).delete(post);
    }

    @Test
    void deletePostNotFound() {
        //GIVEN
        when(postService.findById(1)).thenReturn(empty());
        //WHEN
        boolean deleted = postFacade.delete("code");
        //THEN
        assertFalse(deleted);
    }

    @Test
    void getByCode() {
        //GIVEN
        Post post = Post.builder().slugifiedTitle("code").build();
        PostData postData = PostData.builder().code("code").build();
        when(postService.findById(1)).thenReturn(of(post));
        //WHEN
        Optional<PostData> responsePostData = postFacade.findByCode("code");
        //THEN
        assertTrue(responsePostData.isPresent());
        assertEquals(postData, responsePostData.get());
        verify(postService).findById(1);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}