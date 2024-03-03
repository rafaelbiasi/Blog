package br.com.rafaelbiasi.blog.data;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommentData {

    @EqualsAndHashCode.Include
    private String code;
    @NotBlank(message = "Comment can't be blank")
    private String text;
    private AccountData author;
    private PostData post;
    private LocalDateTime creation;
    private LocalDateTime modified;
}
