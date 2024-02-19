package br.com.rafaelbiasi.blog.facade.mapper.role;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional.RoleCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class RoleDataCodeMapper implements Mapper<Role, RoleData> {

    private final RoleCodeBidiMapper roleCodeBidiMapper;

    public RoleDataCodeMapper(RoleCodeBidiMapper roleCodeBidiMapper
    ) {
        this.roleCodeBidiMapper = roleCodeBidiMapper;
    }

    @Override
    public void map(Role source, RoleData target) throws ConversionException {
        roleCodeBidiMapper.map(source, target);
    }
}
