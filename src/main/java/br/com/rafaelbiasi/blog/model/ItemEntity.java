package br.com.rafaelbiasi.blog.model;

import br.com.rafaelbiasi.blog.model.keygen.TSIDKeyGenerator;
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
    private Long id;
    @Column(nullable = false)
    private LocalDateTime creation;
    @Column(nullable = false)
    private LocalDateTime modified;

    @PrePersist
    void onCreate() {
        creation = LocalDateTime.now();
        modified = creation;
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }
}
