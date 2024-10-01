package br.com.rafaelbiasi.blog.domain.entity;

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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Account extends ItemEntity {

    private static final int TYPE_CODE = 2;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @ToString.Exclude
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public int getTypeCode() {
        return TYPE_CODE;
    }

    public boolean isNew() {
        return this.getId() == null;
    }

    public boolean hasNoHoles() {
        return this.getRoles().isEmpty();
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }
}
