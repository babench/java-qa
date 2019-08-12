package ru.otus.zaikin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.zaikin.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}