package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundException;
import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.ui.controller.ImageController;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void image() throws IOException {
        //GIVEN
        String imageURI = "image.jpg";
        when(fileFacade.load(imageURI)).thenReturn(Optional.of(resource));
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
        Executable executable = () -> imageController.image(imageURI);
        //THEN
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void imageImageURINullError404() {
        //GIVEN
        String imageURI = null;
        //WHEN
        Executable executable = () -> imageController.image(imageURI);
        //THEN
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void imageExceptionInternalServerError() {
        //GIVEN
        String imageURI = "image.jpg";
        when(fileFacade.load(imageURI)).thenThrow(RuntimeException.class);
        //WHEN
        Executable executable = () -> imageController.image(imageURI);
        //THEN
        assertThrows(RuntimeException.class, executable);
        verify(fileFacade).load(imageURI);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}