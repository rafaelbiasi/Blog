package br.com.rafaelbiasi.blog.core.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

	private Long id;
	private String email;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Set<Post> posts = new HashSet<>();
	private Set<Role> roles = new HashSet<>();
	private LocalDateTime creation;
	private LocalDateTime modified;

	public User() {
	}

	public User(String email, String username, String password, String firstName, String lastName) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String email, String username, String password, String firstName, String lastName, Set<Post> posts, Set<Role> roles) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.posts = posts;
		this.roles = roles;
	}

	public User(String email, String username, String password, String firstName, String lastName, Set<Post> posts, Set<Role> roles, LocalDateTime creation, LocalDateTime modified) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.posts = posts;
		this.roles = roles;
		this.creation = creation;
		this.modified = modified;
	}

	public User(Long id, String email, String username, String password, String firstName, String lastName, Set<Post> posts, Set<Role> roles, LocalDateTime creation, LocalDateTime modified) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.posts = posts;
		this.roles = roles;
		this.creation = creation;
		this.modified = modified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
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

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		if (id != null && user.id != null) {
			return Objects.equals(id, user.id);
		}

		return Objects.equals(email, user.email) &&
				Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return id != null ? Objects.hash(id) : Objects.hash(email, username);
	}
}
