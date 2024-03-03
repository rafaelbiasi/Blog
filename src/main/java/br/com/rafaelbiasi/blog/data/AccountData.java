package br.com.rafaelbiasi.blog.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    private Long code;
    @Email(message = "Invalid e-mail")
    @NotBlank(message = "E-mail is mandatory")
    private String email;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @JsonIgnore
    @Builder.Default
    private Set<PostData> posts = new HashSet<>();
    @Builder.Default
    private Set<RoleData> roles = new HashSet<>();
    private LocalDateTime creation;
    private LocalDateTime modified;

    public String getName() {
        return getFirstName() + " " + getLastName();
    }
}
