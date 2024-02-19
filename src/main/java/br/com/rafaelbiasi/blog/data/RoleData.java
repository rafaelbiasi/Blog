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
public class RoleData {

    @EqualsAndHashCode.Include
    long code;
    String name;
    LocalDateTime creation;
    LocalDateTime modified;
}
