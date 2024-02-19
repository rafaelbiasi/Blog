package br.com.rafaelbiasi.blog.data;

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
    String title;
    String body;
    String imageFilePath;
    AccountData account;
    LocalDateTime creation;
    LocalDateTime modified;
}
