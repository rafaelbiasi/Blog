package br.com.rafaelbiasi.blog.facade.mapper.account;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional.AccountCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AccountCodeMapper implements Mapper<AccountData, Account> {

    private final AccountCodeBidiMapper accountCodeBidiMapper;

    public AccountCodeMapper(AccountCodeBidiMapper accountCodeBidiMapper) {
        this.accountCodeBidiMapper = accountCodeBidiMapper;
    }

    @Override
    public void map(AccountData source, Account target) throws ConversionException {
        accountCodeBidiMapper.reverseMap(source, target);
    }
}
