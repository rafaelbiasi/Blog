package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.model.Role;
import br.com.rafaelbiasi.blog.core.service.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class RolesInitialData {

	private final RoleService roleService;
	private final EntityManager entityManager;

	private Role createRole(final String name) {
		val role = new Role(name);
		Role saved = roleService.save(role);
		log.debug("Role created: {}", name);
		return saved;
	}

	public RolesResult createRoles() {
		log.info("Creating default roles");
		val user = createRole("ROLE_USER");
		val admin = createRole("ROLE_ADMIN");
		val guest = createRole("ROLE_GUEST");

//		entityManager.flush();

		return RolesResult.builder().user(user).admin(admin).guest(guest).build();
	}

}