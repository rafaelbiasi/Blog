package br.com.rafaelbiasi.blog.model;

import br.com.rafaelbiasi.blog.entity.ItemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "post")
@DiscriminatorValue("post")
public class Post extends ItemEntity {

    @Column(unique = true, nullable = false)
    String code;
    @Column(nullable = false)
    String title;
    @Column(columnDefinition = "TEXT")
    String body;
    String imageFilePath;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    Account account;
}
