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
public class RoleData {

    @EqualsAndHashCode.Include
    private long code;
    @NotBlank(message = "Role name is mandatory")
    private String name;
    private LocalDateTime creation;
    private LocalDateTime modified;
}
