package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.PostData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface PostFacade {

    List<PostData> findAll();

    Page<PostData> findAll(Pageable pageable);

    Optional<PostData> findById(long id);

    void save(PostData post);

    void save(PostData postData, Principal user);

    void save(PostData post, MultipartFile file);

    void save(PostData postData, MultipartFile file, Principal user);

    boolean delete(String code);

    Optional<PostData> findByCode(String code);
}
