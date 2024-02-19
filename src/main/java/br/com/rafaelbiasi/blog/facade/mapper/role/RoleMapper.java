package br.com.rafaelbiasi.blog.facade.mapper.role;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional.RoleBidiMapper;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper<RoleData, Role> {

    private final RoleBidiMapper roleBidiMapper;

    public RoleMapper(RoleBidiMapper roleBidiMapper) {
        this.roleBidiMapper = roleBidiMapper;
    }

    @Override
    public void map(RoleData source, Role target) throws ConversionException {
        roleBidiMapper.reverseMap(source, target);
    }
}
