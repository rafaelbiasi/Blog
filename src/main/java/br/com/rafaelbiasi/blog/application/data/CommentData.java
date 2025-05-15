package br.com.rafaelbiasi.blog.application.data;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommentData {

	@EqualsAndHashCode.Include
	private String code;
	@NotBlank(message = "Add a comment")
	private String text;
	private UserData author;
	private PostData post;
	private LocalDateTime creation;
	private LocalDateTime modified;
}
