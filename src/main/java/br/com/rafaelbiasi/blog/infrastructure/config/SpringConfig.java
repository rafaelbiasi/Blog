package br.com.rafaelbiasi.blog.infrastructure.config;

import br.com.rafaelbiasi.blog.core.repository.*;
import br.com.rafaelbiasi.blog.core.service.*;
import br.com.rafaelbiasi.blog.core.service.impl.*;
import br.com.rafaelbiasi.blog.infrastructure.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SpringConfig {

	@Bean
	public CommentService commentService(CommentRepository commentRespository, UserService userService, PostService postService) {
		return new CommentServiceImpl(commentRespository, userService, postService);
	}

	@Bean
	public FileService fileService(FileRepository fileRepository) {
		return new FileServiceImpl(fileRepository);
	}

	@Bean
	public PostService postService(PostRepository postRepository, UserService userService) {
		return new PostServiceImpl(postRepository, userService);
	}

	@Bean
	public RoleService roleService(RoleRepository roleRepository) {
		return new RoleServiceImpl(roleRepository);
	}

	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return new UserDetailsServiceImpl(userService);
	}

	@Bean
	public UserService userService(PasswordEncoderService passwordEncoderService, UserRepository userRepository, RoleService roleService) {
		return new UserServiceImpl(passwordEncoderService, userRepository, roleService);
	}

}
