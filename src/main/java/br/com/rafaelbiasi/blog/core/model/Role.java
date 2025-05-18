package br.com.rafaelbiasi.blog.core.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Role {

	private static final int TYPE_CODE = 3;

	private Long id;
	private String name;
	private LocalDateTime creation;
	private LocalDateTime modified;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, LocalDateTime creation, LocalDateTime modified) {
		this.name = name;
		this.creation = creation;
		this.modified = modified;
	}

	public Role(Long id, String name, LocalDateTime creation, LocalDateTime modified) {
		this.id = id;
		this.name = name;
		this.creation = creation;
		this.modified = modified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		return id != null ? Objects.hash(id) : Objects.hash(name);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		if (id != null && role.id != null) {
			return Objects.equals(id, role.id);
		}
		return Objects.equals(name, role.name);
	}
}
