package br.com.rafaelbiasi.blog.domain.model;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "post")
@DiscriminatorValue("post")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Post extends ItemEntity {

    private static final int TYPE_CODE = 1;
    private static final Slugify slugify = Slugify.builder().build();

    @Column(unique = true, nullable = false)
    private String slugifiedTitle;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String imageFilePath;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;
    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @Override
    public int getTypeCode() {
        return TYPE_CODE;
    }

    protected void onCreate() {
        super.onCreate();
        if (ofNullable(getSlugifiedTitle()).isEmpty()) {
            setSlugifiedTitle(slugify.slugify(title));
        }
    }
}
