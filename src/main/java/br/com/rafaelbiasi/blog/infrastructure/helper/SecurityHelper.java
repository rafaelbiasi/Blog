package br.com.rafaelbiasi.blog.infrastructure.helper;

import br.com.rafaelbiasi.blog.core.service.CommentService;
import br.com.rafaelbiasi.blog.core.service.PostService;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

	private final CommentService commentService;
	private final PostService postService;

	public boolean canDeleteComment(String code, Authentication authentication) {
		return commentService.findById(SqidsUtil.decodeId(code))
				.map(comment ->
						hasAdminRole(authentication) ||
								comment.getAuthor().getUsername().equals(authentication.getName()))
				.orElse(false);
	}

	public boolean canDeletePost(String code, Authentication authentication) {
		return postService.findById(SqidsUtil.decodeId(code))
				.map(post ->
						hasAdminRole(authentication) ||
								post.getAuthor().getUsername().equals(authentication.getName()))
				.orElse(false);
	}

	public boolean canDeleteUser(String code, Authentication authentication) {
		return hasAdminRole(authentication);
	}

	private boolean hasAdminRole(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	}
}