package ru.otus.zaikin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.otus.zaikin.config.AppConfig;
import ru.otus.zaikin.entity.Account;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(classes = {AppConfig.class})
public class AccountRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void shouldSaveAndDelete() {
        Account account = new Account("Zaikin", "password");
        assertThat(accountRepository.findAll()).hasSize(0);
        assertThat(account.getId()).isNull();
        account = accountRepository.save(account);
        assertThat(accountRepository.findAll()).hasSize(1);
        assertThat(account.getId()).isNotNull().isGreaterThan(0L);
        accountRepository.deleteAll();
        assertThat(accountRepository.findAll()).hasSize(0);
    }

    @Test
    public void shouldFindByName() {
        Account account = new Account("Zaikin", "password");
        assertThat(accountRepository.findAll()).hasSize(0);
        assertThat(account.getId()).isNull();
        account = accountRepository.save(account);
        assertThat(accountRepository.findAll()).hasSize(1);
        Optional<Account> optionalAccount = accountRepository.findByUsername("Zaikin");
        assertTrue(optionalAccount.isPresent());
        Account foundAccount = optionalAccount.get();
        assertEquals(foundAccount, account);
        accountRepository.deleteAll();
    }
}