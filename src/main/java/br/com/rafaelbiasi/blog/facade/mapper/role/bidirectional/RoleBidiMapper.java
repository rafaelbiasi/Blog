package br.com.rafaelbiasi.blog.facade.mapper.role.bidirectional;

import br.com.rafaelbiasi.blog.data.RoleData;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class RoleBidiMapper implements BidirectionalMapper<Role, RoleData> {

    @Override
    public void reverseMap(RoleData source, Role target) throws ConversionException {
        mapGet(source::getName, target::setName);
        mapGet(source::getCreation, target::setCreation);
        mapGet(source::getModified, target::setModified);
    }

    @Override
    public void map(Role source, RoleData target) throws ConversionException {
        mapGet(source::getName, target::setName);
        mapGet(source::getCreation, target::setCreation);
        mapGet(source::getModified, target::setModified);
    }
}
