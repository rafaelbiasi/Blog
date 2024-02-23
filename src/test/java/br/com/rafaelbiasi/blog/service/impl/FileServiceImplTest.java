package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.repository.FileRepository;
import br.com.rafaelbiasi.blog.service.FileService;
import br.com.rafaelbiasi.blog.specification.impl.ResourceExistsSpecification;
import br.com.rafaelbiasi.blog.specification.impl.ResourceIsReadableSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.*;

class FileServiceImplTest {

    private FileService fileService;
    private AutoCloseable closeable;
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

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        ResourceExistsSpecification resourceExistsSpec = new ResourceExistsSpecification();
        ResourceIsReadableSpecification resourceIsReadableSpec = new ResourceIsReadableSpecification();
        fileService = new FileServiceImpl(fileRepository, resourceExistsSpec, resourceIsReadableSpec);
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
        Resource resourceResponse = fileService.load("filename.png");
        //THEN
        Assertions.assertEquals(resource, resourceResponse);
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
        Resource resourceResponse = fileService.load("filename.png");
        //THEN
        Assertions.assertEquals(resource, resourceResponse);
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
        Executable executable = () -> fileService.load("filename.png");
        //THEN
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, executable);
        Assertions.assertEquals("Could not read the file!", runtimeException.getMessage());
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
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, executable);
        Assertions.assertEquals("Error: message", runtimeException.getMessage());
        verify(fileRepository).resolve("filename.png");
        verify(path).toUri();
        verify(fileRepository).getUrlResource(uri);
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}