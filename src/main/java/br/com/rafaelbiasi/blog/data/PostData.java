package br.com.rafaelbiasi.blog.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostData {

    @EqualsAndHashCode.Include
    private String code;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Body is mandatory")
    private String body;
    private String imageFilePath;
    @NotNull(message = "Account is mandatory")
    private AccountData author;
    private Set<CommentData> comments = new HashSet<>();
    private LocalDateTime creation;
    private LocalDateTime modified;
}
