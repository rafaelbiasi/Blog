package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.PostData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostFacade {

    List<PostData> findAll();

    Page<PostData> findAll(Pageable pageable);

    Optional<PostData> findById(long id);

    PostData save(PostData post);

    PostData save(PostData post, MultipartFile file);

    boolean delete(String code);

    Optional<PostData> findByCode(String code);
}
