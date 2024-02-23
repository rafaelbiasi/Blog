package br.com.rafaelbiasi.blog.model;

import br.com.rafaelbiasi.blog.entity.ItemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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
