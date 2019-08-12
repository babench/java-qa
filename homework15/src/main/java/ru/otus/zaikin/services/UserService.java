package ru.otus.zaikin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.repository.AccountRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account add(Account account) {
        return accountRepository.save(account);
    }

    public void remove(Long userId) {
        accountRepository.deleteById(userId);
    }

    public Optional<Account> findById(Long userId) {
        return accountRepository.findById(userId);
    }
}