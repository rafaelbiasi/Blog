package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.service.FileService;
import br.com.rafaelbiasi.blog.infrastructure.util.LogId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {

	private final FileService fileService;
	private final RolesInitialData rolesInitialData;
	private final UsersInitialData usersInitialData;
	private final PostsInitialData postsInitialData;

	@Override
	public void run(final String... args) {
		try {
			LogId.startLogId();
			if (postsInitialData.findAll().isEmpty()) {
				log.info("Starting application data initialization");
				fileService.init();
				log.info("File service initialization completed");
				val roles = rolesInitialData.createRoles();
				val users = usersInitialData.createUsers(roles);
				postsInitialData.createPosts(users);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			LogId.endLogId();
		}
	}

	private void createPosts(UsersResult result) {
		postsInitialData.createPosts(result);
	}

}
