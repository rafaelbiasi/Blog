package br.com.rafaelbiasi.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "account")
@DiscriminatorValue("account")
@ToString(exclude = "password")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Account extends ItemEntity {

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    public boolean isNew() {
        return this.getId() == null;
    }

    public boolean hasNoHoles() {
        return this.getRoles().isEmpty();
    }

    public boolean isAdmin() {
        return getRoles()
                .parallelStream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

}
