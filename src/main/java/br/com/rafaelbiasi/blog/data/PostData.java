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
public class PostData {

    @EqualsAndHashCode.Include
    String code;
    @NotBlank(message = "Title is mandatory")
    String title;
    @NotBlank(message = "Body is mandatory")
    String body;
    String imageFilePath;
    AccountData account;
    LocalDateTime creation;
    LocalDateTime modified;
}
