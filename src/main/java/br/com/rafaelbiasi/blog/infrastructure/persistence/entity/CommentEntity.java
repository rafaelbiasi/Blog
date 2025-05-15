package br.com.rafaelbiasi.blog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "comment")
@DiscriminatorValue("comment")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CommentEntity extends ItemEntity {

	private static final int TYPE_CODE = 4;

	@Column(nullable = false)
	private String text;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity author;
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	private PostEntity post;

	@Override
	public int getTypeCode() {
		return TYPE_CODE;
	}
}