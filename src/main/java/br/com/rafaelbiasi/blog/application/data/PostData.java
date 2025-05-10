package br.com.rafaelbiasi.blog.application.data;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostData {

    @EqualsAndHashCode.Include
    private String code;
    private String slugifiedTitle;
    @NotBlank(message = "Provide a title")
    private String title;
    @NotBlank(message = "Provide a Body")
    private String body;
    private String imageFilePath;
    private UserData author;
    @Builder.Default
    private Set<CommentData> comments = new HashSet<>();
    private LocalDateTime creation;
    private LocalDateTime modified;
}
