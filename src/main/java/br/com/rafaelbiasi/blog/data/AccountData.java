package br.com.rafaelbiasi.blog.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
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
public class AccountData {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @EqualsAndHashCode.Include
    private Long code;
    @Email(message = "Invalid e-mail")
    @NotBlank(message = "Provide the e-mail")
    private String email;
    @NotBlank(message = "Enter a username")
    private String username;
    @NotBlank(message = "Create a password")
    private String password;
    @NotBlank(message = "Provide the first name")
    private String firstName;
    @NotBlank(message = "Provide the last name")
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

    public boolean isAdmin() {
        return getRoles()
                .parallelStream()
                .anyMatch(role -> role.getName().equals(ROLE_ADMIN));
    }

}
