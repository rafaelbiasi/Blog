package br.com.rafaelbiasi.blog.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"posts"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountData {

    @EqualsAndHashCode.Include
    Long code;
    @Email
    String email;
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @JsonIgnore
    @Builder.Default
    List<PostData> posts = new ArrayList<>();
    @Builder.Default
    Set<RoleData> roles = new HashSet<>();
    LocalDateTime creation;
    LocalDateTime modified;

    public String getName() {
        return getFirstName() + " " + getLastName();
    }
}
