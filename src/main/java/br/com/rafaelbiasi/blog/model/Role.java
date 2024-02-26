package br.com.rafaelbiasi.blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@jakarta.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "role")
@DiscriminatorValue("role")
public class Role extends ItemEntity {

    @Column(length = 16, nullable = false)
    String name;
}
