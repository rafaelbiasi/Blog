package br.com.rafaelbiasi.blog.facade.mapper.account;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional.AccountCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class AccountDataCodeMapper implements Mapper<Account, AccountData> {

    private final AccountCodeBidiMapper accountCodeBidiMapper;

    public AccountDataCodeMapper(AccountCodeBidiMapper accountCodeBidiMapper) {
        this.accountCodeBidiMapper = accountCodeBidiMapper;
    }

    @Override
    public void map(Account source, AccountData target) throws ConversionException {
        accountCodeBidiMapper.map(source, target);
    }
}
