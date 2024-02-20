package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
        Page<PostData> page = new PageImpl<>(new ArrayList<>());
        when(postFacade.getAll(any())).thenReturn(page);
        //WHEN
        String view = homeController.home(Optional.empty(), 5, model);
        //THEN
        Assertions.assertEquals("home", view);
    }

    @Test
    void homeError() {
        //GIVEN
        when(postFacade.getAll(any())).thenThrow(RuntimeException.class);
        //WHEN
        String view = homeController.home(Optional.empty(), 5, model);
        //THEN
        Assertions.assertEquals("error403", view);
    }
}