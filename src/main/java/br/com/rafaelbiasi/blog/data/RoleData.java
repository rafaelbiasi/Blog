package br.com.rafaelbiasi.blog.data;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleData {

    @EqualsAndHashCode.Include
    private long code;
    @NotBlank(message = "Provide a Role name")
    private String name;
    private LocalDateTime creation;
    private LocalDateTime modified;

}
