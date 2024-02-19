package br.com.rafaelbiasi.blog.config.transformer;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.mapper.account.AccountCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.account.AccountDataCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.account.AccountDataMapper;
import br.com.rafaelbiasi.blog.facade.mapper.account.AccountMapper;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AccountTransformerConfig {

    private final AccountDataMapper accountDataMapper;
    private final AccountMapper accountMapper;
    private final AccountDataCodeMapper accountDataCodeMapper;
    private final AccountCodeMapper accountCodeMapper;

    public AccountTransformerConfig(AccountDataMapper accountDataMapper, AccountMapper accountMapper, AccountDataCodeMapper accountDataCodeMapper, AccountCodeMapper accountCodeMapper) {
        this.accountDataMapper = accountDataMapper;
        this.accountMapper = accountMapper;
        this.accountDataCodeMapper = accountDataCodeMapper;
        this.accountCodeMapper = accountCodeMapper;
    }

    @Bean("accountDataTransformer")
    public Transformer<Account, AccountData> accountDataTransformer() {
        Transformer<Account, AccountData> transformer = new Transformer<>(AccountData.class);
        transformer.setMappers(List.of(
                accountDataCodeMapper,
                accountDataMapper
        ));
        return transformer;
    }

    @Bean("accountTransformer")
    public Transformer<AccountData, Account> accountTransformer() {
        Transformer<AccountData, Account> accountAccountDataTransformer = new Transformer<>(Account.class);
        accountAccountDataTransformer.setMappers(List.of(
                accountCodeMapper,
                accountMapper
        ));
        return accountAccountDataTransformer;
    }
}
