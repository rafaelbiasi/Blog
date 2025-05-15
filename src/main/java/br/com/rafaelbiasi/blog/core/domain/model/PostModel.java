package br.com.rafaelbiasi.blog.core.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PostModel {

	private Long id;
	private String slugifiedTitle;
	private String title;
	private String body;
	private String imageFilePath;
	private UserModel author;
	private Set<CommentModel> comments = new HashSet<>();
	private LocalDateTime creation;
	private LocalDateTime modified;

	public PostModel() {
	}

	public PostModel(String title, String body, String imageFilePath, UserModel author) {
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
	}

	public PostModel(String slugifiedTitle, String title, String body, String imageFilePath, UserModel author) {
		this.slugifiedTitle = slugifiedTitle;
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
	}

	public PostModel(String slugifiedTitle, String title, String body, String imageFilePath, UserModel author, Set<CommentModel> comments) {
		this.slugifiedTitle = slugifiedTitle;
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
		this.comments = comments;
	}

	public PostModel(Long id, String slugifiedTitle, String title, String body, String imageFilePath, UserModel author, Set<CommentModel> comments, LocalDateTime creation, LocalDateTime modified) {
		this.id = id;
		this.slugifiedTitle = slugifiedTitle;
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
		this.comments = comments;
		this.creation = creation;
		this.modified = modified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSlugifiedTitle() {
		return slugifiedTitle;
	}

	public void setSlugifiedTitle(String slugifiedTitle) {
		this.slugifiedTitle = slugifiedTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
	}

	public Set<CommentModel> getComments() {
		return comments;
	}

	public void setComments(Set<CommentModel> comments) {
		this.comments = comments;
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
		return id != null ? Objects.hash(id) : Objects.hash(slugifiedTitle);

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PostModel post = (PostModel) o;
		if (id != null && post.id != null) {
			return Objects.equals(id, post.id);
		}

		return Objects.equals(slugifiedTitle, post.slugifiedTitle);
	}
}
