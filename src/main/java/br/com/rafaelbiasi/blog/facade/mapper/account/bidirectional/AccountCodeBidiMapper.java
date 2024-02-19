package br.com.rafaelbiasi.blog.facade.mapper.account.bidirectional;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.impl.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class AccountCodeBidiMapper implements BidirectionalMapper<Account, AccountData> {

    @Override
    public void map(Account source, AccountData target) throws ConversionException {
        mapGet(source::getId, target::setCode);
    }

    @Override
    public void reverseMap(AccountData source, Account target) throws ConversionException {
        mapGet(source::getCode, target::setId);
    }
}
