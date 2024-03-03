package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.facade.FileFacade;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImageControllerTest {

    private ImageController imageController;
    @Mock
    private FileFacade fileFacade;
    @Mock
    private ServletContext servletContext;
    @Mock
    private Resource resource;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        imageController = new ImageController(fileFacade, servletContext);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void image() {
        //GIVEN
        String imageURI = "image.jpg";
        when(fileFacade.load(imageURI)).thenReturn(resource);
        //WHEN
        ResponseEntity<Resource> image = imageController.image(imageURI);
        //THEN
        assertEquals(resource, image.getBody());
        verify(fileFacade).load(imageURI);
    }

    @Test
    void imageImageURIEmptyError404() {
        //GIVEN
        String imageURI = "";
        //WHEN
        ResponseEntity<Resource> image = imageController.image(imageURI);
        //THEN
        assertEquals(HttpStatusCode.valueOf(404), image.getStatusCode());
    }

    @Test
    void imageImageURINullError404() {
        //GIVEN
        String imageURI = null;
        //WHEN
        ResponseEntity<Resource> image = imageController.image(imageURI);
        //THEN
        assertEquals(HttpStatusCode.valueOf(404), image.getStatusCode());
    }

    @Test
    void imageExceptionInternalServerError() {
        //GIVEN
        String imageURI = "image.jpg";
        when(fileFacade.load(imageURI)).thenThrow(RuntimeException.class);
        //WHEN
        ResponseEntity<Resource> image = imageController.image(imageURI);
        //THEN
        assertEquals(HttpStatusCode.valueOf(500), image.getStatusCode());
        verify(fileFacade).load(imageURI);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}