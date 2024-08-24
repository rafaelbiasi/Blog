package br.com.rafaelbiasi.blog.facade.mapper.account;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional.AccountBidiMapper;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AccountDataMapper implements Mapper<Account, AccountData> {

    private final AccountBidiMapper bidiMapper;

    public AccountDataMapper(AccountBidiMapper bidiMapper) {
        this.bidiMapper = bidiMapper;
    }

    @Override
    public void map(Account source, AccountData target) throws ConversionException {
        bidiMapper.map(source, target);
    }
}
