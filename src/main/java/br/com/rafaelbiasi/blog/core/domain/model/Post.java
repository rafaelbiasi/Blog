package br.com.rafaelbiasi.blog.core.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Post {

	private Long id;
	private String slugifiedTitle;
	private String title;
	private String body;
	private String imageFilePath;
	private User author;
	private Set<Comment> comments = new HashSet<>();
	private LocalDateTime creation;
	private LocalDateTime modified;

	public Post() {
	}

	public Post(String title, String body, String imageFilePath, User author) {
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
	}

	public Post(String slugifiedTitle, String title, String body, String imageFilePath, User author) {
		this.slugifiedTitle = slugifiedTitle;
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
	}

	public Post(String slugifiedTitle, String title, String body, String imageFilePath, User author, Set<Comment> comments) {
		this.slugifiedTitle = slugifiedTitle;
		this.title = title;
		this.body = body;
		this.imageFilePath = imageFilePath;
		this.author = author;
		this.comments = comments;
	}

	public Post(Long id, String slugifiedTitle, String title, String body, String imageFilePath, User author, Set<Comment> comments, LocalDateTime creation, LocalDateTime modified) {
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
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
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		if (id != null && post.id != null) {
			return Objects.equals(id, post.id);
		}

		return Objects.equals(slugifiedTitle, post.slugifiedTitle);
	}

	@Override
	public int hashCode() {
		return id != null ? Objects.hash(id) : Objects.hash(slugifiedTitle);

	}
}
