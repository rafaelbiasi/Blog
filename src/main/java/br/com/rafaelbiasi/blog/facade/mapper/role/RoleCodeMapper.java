package br.com.rafaelbiasi.blog.facade.mapper.role;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional.RoleCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RoleCodeMapper implements Mapper<RoleData, Role> {

    private final RoleCodeBidiMapper roleCodeBidiMapper;

    public RoleCodeMapper(RoleCodeBidiMapper roleCodeBidiMapper) {
        this.roleCodeBidiMapper = roleCodeBidiMapper;
    }

    @Override
    public void map(RoleData source, Role target) throws ConversionException {
        roleCodeBidiMapper.reverseMap(source, target);
    }
}
