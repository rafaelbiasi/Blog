package br.com.rafaelbiasi.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@DiscriminatorValue("account")
@ToString(exclude = "password")
public class Account extends ItemEntity {

    @Column(unique = true, nullable = false)
    String email;
    @Column(unique = true, nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "account")
    List<Post> posts = new ArrayList<>();
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    Set<Role> roles = new HashSet<>();
}
