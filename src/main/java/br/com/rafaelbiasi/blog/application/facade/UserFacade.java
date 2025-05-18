package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.core.vo.RegistrationResponse;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

	SimplePage<UserData> findAll(SimplePageRequest pageable);

	Optional<UserData> findByCode(String code);

	Optional<UserData> findOneByEmail(final String email);

	Optional<UserData> findOneByUsername(final String username);

	UserData save(final UserData userData);

	void registerUser(final UserData userData);

	RegistrationResponse checkEmailAndUsernameExists(final UserData userData);

	boolean delete(String code);

	List<RoleData> listAllRoles();
}
