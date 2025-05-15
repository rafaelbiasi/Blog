package br.com.rafaelbiasi.blog.core.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentModel {

	private Long id;
	private String text;
	private UserModel author;
	private PostModel post;
	private LocalDateTime creation;
	private LocalDateTime modified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
	}

	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
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
		return id != null ? Objects.hash(id) : Objects.hash(text, author, post);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		CommentModel comment = (CommentModel) o;
		if (id != null && comment.id != null) {
			return Objects.equals(id, comment.id);
		}

		return Objects.equals(text, comment.text) &&
				Objects.equals(author, comment.author) &&
				Objects.equals(post, comment.post);
	}
}