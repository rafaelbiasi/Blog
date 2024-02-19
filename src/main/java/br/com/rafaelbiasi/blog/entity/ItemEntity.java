package br.com.rafaelbiasi.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entity")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ItemEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "tsid_gen")
    @GenericGenerator(name = "tsid_gen", type = TSIDKeyGenerator.class)
    Long id;

    @Column(nullable = false)
    LocalDateTime creation;

    @Column(nullable = false)
    LocalDateTime modified;

    @PrePersist
    protected void onCreate() {
        creation = LocalDateTime.now();
        modified = creation;
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }
}
