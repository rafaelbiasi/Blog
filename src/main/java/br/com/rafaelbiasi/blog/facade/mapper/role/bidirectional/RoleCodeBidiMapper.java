package br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.exception.ConversionException;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleCodeBidiMapper implements BidirectionalMapper<Role, RoleData> {

    @Override
    public void reverseMap(RoleData source, Role target) throws ConversionException {
        mapGet(source::getCode, target::setId);
    }

    @Override
    public void map(Role source, RoleData target) throws ConversionException {
        mapGet(source::getId, target::setCode);
    }
}
