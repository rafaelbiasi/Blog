package br.com.rafaelbiasi.blog.facade;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Defines the contract for a facade that abstracts file handling operations.
 * The FileFacade provides a simplified interface for clients to interact with file storage
 * functionalities, such as saving uploaded files and loading files as resources.
 * This abstraction allows for decoupling clients from the underlying file storage implementation details.
 */
public interface FileFacade {

    /**
     * Loads a file as a {@link Resource} based on the provided URI.
     * This method abstracts the process of retrieving files from the storage mechanism,
     * allowing clients to access files without knowing their physical storage locations.
     *
     * @param imageUri the URI or path of the file to load
     * @return a {@link Resource} representing the loaded file
     */
    Resource load(String imageUri);

    /**
     * Saves an uploaded file to the storage mechanism.
     * This method abstracts the details of how files are stored, providing a simple interface
     * for clients to save files without concerning themselves with storage specifics.
     *
     * @param file the {@link MultipartFile} representing the uploaded file to be saved
     */
    void save(MultipartFile file);
}
