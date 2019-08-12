package ru.otus.zaikin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.otus.zaikin.config.AppConfig;
import ru.otus.zaikin.entity.Account;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {AppConfig.class, UserService.class})
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserService userService;

    @Test
    public void shouldFindAll() {
        Collection<Account> accounts = userService.findAll();
        assertThat(accounts).hasSize(0);
    }

    @Test
    public void shouldAddFindRemove() {
        Account account = new Account("Zaikin", "Password");
        account = userService.add(account);
        assertThat(account.getId()).isNotNull().isGreaterThan(0L);
        assertThat(userService.findAll()).hasSize(1);
        userService.remove(account.getId());
        assertThat(userService.findAll()).hasSize(0);
    }
}