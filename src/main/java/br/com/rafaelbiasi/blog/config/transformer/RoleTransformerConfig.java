package br.com.rafaelbiasi.blog.config.transformer;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.facade.mapper.role.RoleCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.role.RoleDataCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.role.RoleDataMapper;
import br.com.rafaelbiasi.blog.facade.mapper.role.RoleMapper;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RoleTransformerConfig {

    private final RoleDataMapper roleDataMapper;
    private final RoleMapper roleMapper;
    private final RoleDataCodeMapper roleDataCodeMapper;
    private final RoleCodeMapper roleCodeMapper;

    @Bean("roleDataTransformer")
    public Transformer<Role, RoleData> roleDataTransformer() {
        Transformer<Role, RoleData> transformer = new Transformer<>(RoleData.class);
        transformer.setMappers(List.of(
                roleDataCodeMapper,
                roleDataMapper
        ));
        return transformer;
    }

    @Bean("roleTransformer")
    public Transformer<RoleData, Role> roleTransformer() {
        Transformer<RoleData, Role> transformer = new Transformer<>(Role.class);
        transformer.setMappers(List.of(
                roleCodeMapper,
                roleMapper
        ));
        return transformer;
    }
}
