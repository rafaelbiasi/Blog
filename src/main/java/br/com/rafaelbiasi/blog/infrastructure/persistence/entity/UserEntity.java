package br.com.rafaelbiasi.blog.infrastructure.persistence.entity;

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
@Table(name = "user")
@DiscriminatorValue("user")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserEntity extends ItemEntity {

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
	private Set<PostEntity> posts = new HashSet<>();
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private Set<RoleEntity> roles = new HashSet<>();

	@Override
	public int getTypeCode() {
		return TYPE_CODE;
	}


}
