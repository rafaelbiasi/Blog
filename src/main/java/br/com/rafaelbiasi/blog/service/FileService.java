package br.com.rafaelbiasi.blog.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Defines the contract for file handling services.
 * This interface provides methods for initializing storage, saving uploaded files,
 * and loading files as resources. It is designed to abstract the underlying storage mechanism,
 * allowing for different implementations such as local file system storage, cloud storage, etc.
 */
public interface FileService {

    /**
     * Initializes the storage mechanism.
     * This method should set up the necessary infrastructure for storing files,
     * such as creating directories or ensuring permissions in a filesystem-based implementation,
     * or configuring buckets in a cloud storage-based implementation.
     */
    void init();

    /**
     * Saves a file that has been uploaded.
     * The implementation should handle the storage of the file in the configured storage mechanism,
     * ensuring that the file is persisted and accessible for future retrieval.
     *
     * @param file the {@link MultipartFile} representing the uploaded file to be saved
     */
    void save(MultipartFile file);

    /**
     * Loads a file as a {@link Resource}.
     * This method should retrieve the file identified by the filename from the storage mechanism,
     * and return it as a {@link Resource}, which provides a handle for reading the file contents.
     *
     * @param filename the name of the file to load
     * @return a {@link Resource} representing the loaded file
     */
    Resource load(String filename);
}
