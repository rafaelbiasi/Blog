package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeControllerTest {


    private HomeController homeController;
    @Mock
    private PostFacade postFacade;
    @Mock
    private Model model;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        homeController = new HomeController(postFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void home() {
        //GIVEN
        List<PostData> postDatas = Collections.singletonList(PostData.builder().code("code").build());
        Page<PostData> page = new PageImpl<>(postDatas);
        when(postFacade.findAll(any())).thenReturn(page);
        //WHEN
        String view = homeController.home(empty(), 5, model);
        //THEN
        assertEquals("home", view);
        verify(postFacade).findAll(any());
        verify(model).addAttribute("posts", postDatas);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 1);
        verify(model).addAttribute("size", 5);
    }

    @Test
    void homeError() {
        //GIVEN
        when(postFacade.findAll(any())).thenThrow(RuntimeException.class);
        //WHEN
        String view = homeController.home(empty(), 5, model);
        //THEN
        assertEquals("error403", view);
        verify(postFacade).findAll(any());
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}