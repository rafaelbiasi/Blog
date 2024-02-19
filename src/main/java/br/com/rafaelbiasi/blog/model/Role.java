package br.com.rafaelbiasi.blog.model;

import br.com.rafaelbiasi.blog.entity.ItemEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Table;
import lombok.*;

@jakarta.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "role")
@DiscriminatorValue("role")
public class Role extends ItemEntity {

    @Column(length = 16, nullable = false)
    String name;
}
