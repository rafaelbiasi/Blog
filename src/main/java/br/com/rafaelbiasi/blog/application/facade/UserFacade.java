package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

	Page<UserData> findAll(Pageable pageable);

	Optional<UserData> findByCode(String code);

	Optional<UserData> findOneByEmail(final String email);

	Optional<UserData> findOneByUsername(final String username);

	UserData save(final UserData userData);

	void registerUser(final UserData userData);

	RegistrationResponseModel checkEmailAndUsernameExists(final UserData userData);

	boolean delete(String code);

	List<RoleData> listAllRoles();
}
