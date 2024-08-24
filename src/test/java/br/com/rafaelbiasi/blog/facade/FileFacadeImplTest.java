package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileFacadeImplTest {

    private FileFacade fileFacade;
    @Mock
    private FileService fileService;
    @Mock
    private Resource resource;
    @Mock
    private MultipartFile file;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        fileFacade = new FileFacadeImpl(fileService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test()
    void load() {
        //GIVEN
        String imageURI = "imageURI";
        when(fileService.load(imageURI)).thenReturn(Optional.of(resource));
        //WHEN
        Optional<Resource> load = fileFacade.load(imageURI);
        //THEN
        assertTrue(load.isPresent());
        assertEquals(resource, load.get());
        verify(fileService).load(imageURI);
    }

    @Test()
    void save() throws IOException {
        //GIVEN
        //WHEN
        fileFacade.save(file);
        //THEN
        verify(fileService).save(file);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}