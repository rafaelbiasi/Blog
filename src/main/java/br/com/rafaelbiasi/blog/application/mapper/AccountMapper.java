package br.com.rafaelbiasi.blog.application.mapper;

import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RoleMapper.class}
)
public interface AccountMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "id", target = "code")
    AccountData accountToAccountData(Account account);

    @Mapping(source = "code", target = "id")
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Account accountDataToAccount(AccountData accountData);
}