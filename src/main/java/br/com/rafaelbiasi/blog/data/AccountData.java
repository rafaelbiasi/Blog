package br.com.rafaelbiasi.blog.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
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
    long code;
    String email;
    String username;
    String password;
    String firstName;
    String lastName;
    @JsonIgnore
    List<PostData> posts;
    Set<RoleData> roles = new HashSet<>();
    LocalDateTime creation;
    LocalDateTime modified;

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

}
