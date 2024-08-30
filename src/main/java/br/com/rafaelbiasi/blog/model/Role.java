package br.com.rafaelbiasi.blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "role")
@DiscriminatorValue("role")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends ItemEntity {

    @Column(length = 16, nullable = false)
    private String name;

}
