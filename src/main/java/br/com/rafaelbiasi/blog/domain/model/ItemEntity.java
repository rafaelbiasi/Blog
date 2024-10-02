package br.com.rafaelbiasi.blog.domain.model;

import br.com.rafaelbiasi.blog.infrastructure.keygen.TSIDKeyIdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "entity")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ItemEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "tsid_gen")
    @TSIDKeyIdGenerator
    private Long id;
    @Column(nullable = false)
    private LocalDateTime creation;
    @Column(nullable = false)
    private LocalDateTime modified;

    public abstract int getTypeCode();

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
