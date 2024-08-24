package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.repository.FileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    private FileService fileService;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private MultipartFile file;
    @Mock
    private Resource resource;
    @Mock
    private Path path;
    @Mock
    private URI uri;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        fileService = new FileServiceImpl(fileRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void init() throws IOException {
        //GIVEN
        //WHEN
        fileService.init();
        //THEN
        verify(fileRepository).createDirectories();
    }

    @Test
    void save() throws IOException {
        //GIVEN
        when(fileRepository.resolve("filename.png")).thenReturn(path);
        when(file.getOriginalFilename()).thenReturn("filename.png");
        //WHEN
        fileService.save(file);
        //THEN
        verify(file).getOriginalFilename();
        verify(fileRepository).copy(any(), any());
    }

    @Test
    void loadResourceExists() throws MalformedURLException {
        //GIVEN
        when(fileRepository.resolve("filename.png")).thenReturn(path);
        when(path.toUri()).thenReturn(uri);
        when(fileRepository.getUrlResource(uri)).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        //WHEN
        Optional<Resource> resourceResponse = fileService.load("filename.png");
        //THEN
        assertTrue(resourceResponse.isPresent());
        assertEquals(resource, resourceResponse.get());
        verify(fileRepository).resolve("filename.png");
        verify(path).toUri();
        verify(fileRepository).getUrlResource(uri);
        verify(resource).exists();
    }

    @Test
    void loadResourceNotExistsAndIsReadable() throws MalformedURLException {
        //GIVEN
        when(fileRepository.resolve("filename.png")).thenReturn(path);
        when(path.toUri()).thenReturn(uri);
        when(fileRepository.getUrlResource(uri)).thenReturn(resource);
        when(resource.exists()).thenReturn(false);
        when(resource.isReadable()).thenReturn(true);
        //WHEN
        Optional<Resource> resourceResponse = fileService.load("filename.png");
        //THEN
        assertTrue(resourceResponse.isPresent());
        assertEquals(resource, resourceResponse.get());
        verify(fileRepository).resolve("filename.png");
        verify(path).toUri();
        verify(fileRepository).getUrlResource(uri);
        verify(resource).exists();
        verify(resource).isReadable();
    }

    @Test
    void loadResourceNotExistsAndNotIsReadable() throws MalformedURLException {
        //GIVEN
        when(fileRepository.resolve("filename.png")).thenReturn(path);
        when(path.toUri()).thenReturn(uri);
        when(fileRepository.getUrlResource(uri)).thenReturn(resource);
        when(resource.exists()).thenReturn(false);
        when(resource.isReadable()).thenReturn(false);
        //WHEN
        Optional<Resource> loaded = fileService.load("filename.png");
        //THEN
        assertTrue(loaded.isEmpty());
        verify(fileRepository).resolve("filename.png");
        verify(path).toUri();
        verify(fileRepository).getUrlResource(uri);
        verify(resource).exists();
        verify(resource).isReadable();
    }

    @Test
    void loadResourceThrowMalformedURLException() throws MalformedURLException {
        //GIVEN
        when(fileRepository.resolve("filename.png")).thenReturn(path);
        when(path.toUri()).thenReturn(uri);
        when(fileRepository.getUrlResource(uri)).thenThrow(new MalformedURLException("message"));
        //WHEN
        Executable executable = () -> fileService.load("filename.png");
        //THEN
        assertThrows(MalformedURLException.class, executable);
        verify(fileRepository).resolve("filename.png");
        verify(path).toUri();
        verify(fileRepository).getUrlResource(uri);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}