package br.com.rafaelbiasi.blog.facade.mapper.account;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional.AccountBidiMapper;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<AccountData, Account> {

    private final AccountBidiMapper bidiMapper;

    public AccountMapper(AccountBidiMapper bidiMapper) {
        this.bidiMapper = bidiMapper;
    }

    @Override
    public void map(AccountData source, Account target) throws ConversionException {
        bidiMapper.reverseMap(source, target);
    }
}
