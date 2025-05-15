package br.com.rafaelbiasi.blog.application.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserData {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	@EqualsAndHashCode.Include
	private String code;
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
	@Builder.Default
	private Set<RoleData> roles = new HashSet<>();
	private LocalDateTime creation;
	private LocalDateTime modified;

	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	public String getRolesString() {
		return String.join(",", roles.stream().map(RoleData::getName).toArray(String[]::new));
	}

	public List<String> getRolesStr() {
		return roles.stream().map(RoleData::getCode).toList();
	}

	public void setRolesStr(List<String> roles) {
		roles.stream().map(RoleData::new).forEach(this.roles::add);
	}
}
