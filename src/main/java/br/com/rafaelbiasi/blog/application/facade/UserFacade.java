package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponseModel;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

	PageModel<UserData> findAll(PageRequestModel pageable);

	Optional<UserData> findByCode(String code);

	Optional<UserData> findOneByEmail(final String email);

	Optional<UserData> findOneByUsername(final String username);

	UserData save(final UserData userData);

	void registerUser(final UserData userData);

	RegistrationResponseModel checkEmailAndUsernameExists(final UserData userData);

	boolean delete(String code);

	List<RoleData> listAllRoles();
}
