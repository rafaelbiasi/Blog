package br.com.rafaelbiasi.blog.facade.mapper.role;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional.RoleBidiMapper;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class RoleDataMapper implements Mapper<Role, RoleData> {

    private final RoleBidiMapper roleBidiMapper;

    public RoleDataMapper(RoleBidiMapper roleBidiMapper) {
        this.roleBidiMapper = roleBidiMapper;
    }

    @Override
    public void map(Role source, RoleData target) throws ConversionException {
        roleBidiMapper.map(source, target);
    }
}
