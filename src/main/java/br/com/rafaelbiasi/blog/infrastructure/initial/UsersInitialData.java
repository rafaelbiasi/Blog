package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.model.Role;
import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class UsersInitialData {

	private final UserService userService;
	private final EntityManager entityManager;

	public UsersResult createUsers(final RolesResult roles) {
		log.info("Creating default users");
		val user = createUser(
				"User",
				"Resu",
				"user@domain.com",
				"user",
				"resu",
				roles.user()
		);
		val admin = createUser(
				"Admin",
				"Nimda",
				"admin@domain.com",
				"admin",
				"nimda",
				roles.admin()
		);
		val guest = createUser(
				"Guest",
				"Tseug",
				"guest@domain.com",
				"guest",
				"tseug",
				roles.guest()
		);

//		entityManager.flush();

		return UsersResult.builder().user(user).admin(admin).guest(guest).build();
	}

	private User createUser(
			final String userFirst,
			final String userLast,
			final String mail,
			final String username,
			final String password,
			final Role role
	) {
		val user = new User(mail, username, password, userFirst, userLast);
		val roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		User saved = userService.save(user);
		log.debug("User created: {}", username);
		return saved;
	}
}