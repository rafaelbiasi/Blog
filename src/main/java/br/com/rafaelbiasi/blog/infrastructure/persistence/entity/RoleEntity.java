package br.com.rafaelbiasi.blog.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "role")
@DiscriminatorValue("role")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RoleEntity extends ItemEntity {

    private static final int TYPE_CODE = 3;

    @Column(length = 16, nullable = false)
    private String name;

    @Override
    public int getTypeCode() {
        return TYPE_CODE;
    }
}
