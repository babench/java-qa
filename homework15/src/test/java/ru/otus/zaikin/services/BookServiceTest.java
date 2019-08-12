package ru.otus.zaikin.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.otus.zaikin.config.AppConfig;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.repository.AccountRepository;
import ru.otus.zaikin.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {AppConfig.class, UserService.class, BookService.class})
@Log4j2
public class BookServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void shouldAdd() {
        Account account = new Account("ZAIKIN1", "pas");
        account = userService.add(account);
        List<Book> books1 = bookService.getBooks(account.getId());
        assertThat(books1).hasSize(0);
        Book book1 = bookService.add(account.getId(), new Book(account, "Onegin", "Pushkin", "Book 1"));
        assertThat(bookService.getBooks(account.getId())).hasSize(1);
        Book book2 = bookService.add(account.getId(), new Book(account, "Mednyj vsadnik", "Pushkin", "Book 2"));
        assertThat(bookService.getBooks(account.getId())).hasSize(2);
        assertThat(book1.getId()).isNotNull().isGreaterThan(0L);
        assertThat(book2.getId()).isNotNull().isGreaterThan(0L);
        assertThat(book1.getAccount()).isNotNull();
        assertThat(book1.getAccount().getId()).isNotNull();
        assertThat(book2.getAccount()).isNotNull();
        assertThat(book2.getAccount().getId()).isNotNull();
        assertThat(book1.getAccount().getId()).isEqualTo(book1.getAccount().getId());
        account = userService.findById(account.getId()).get();
        assertThat(account.getBooks()).hasSize(2);
        account.getBooks().removeAll(account.getBooks());
        account = accountRepository.save(account);
        account = userService.findById(account.getId()).get();
        assertThat(account.getBooks()).hasSize(0);
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(0);
        accountRepository.delete(account);
    }

    @Test
    public void shouldPut() {
        Account account = new Account("ZAIKIN2", "pas");
        account = userService.add(account);
        Book book1 = bookService.add(account.getId(), new Book(account, "Onegin", "Pushkin", "Book 1"));
        Book book2 = bookService.add(account.getId(), new Book(account, "Mednyj vsadnik", "Pushkin", "Book 2"));

        account = userService.findById(account.getId()).get();
        assertThat(bookService.getBooks(account.getId())).hasSize(2);
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(2);

        List<Book> newBooks = new ArrayList<>();
        Book book3 = bookService.add(account.getId(), new Book(account, "Parus", "Lermontov", "Book 1"));
        Book book4 = bookService.add(account.getId(), new Book(account, "Prestuplenie i nakazanie", "Dostoevskiy", "Book 2"));
        Book book5 = bookService.add(account.getId(), new Book(account, "Nos", "Gogol'", "Book 3"));
        newBooks.add(book3);
        newBooks.add(book4);
        newBooks.add(book5);
        bookService.put(account.getId(), newBooks);
        account = userService.findById(account.getId()).get();

        assertThat(account.getBooks()).hasSize(3);
        bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(3);
        accountRepository.delete(account);
    }

    @Test
    public void shouldCascaseDelete() {
        Account account = new Account("ZAIKIN3", "pas");
        account = userService.add(account);
        Book book1 = bookService.add(account.getId(), new Book(account, "Onegin", "Pushkin", "Book 1"));
        Book book2 = bookService.add(account.getId(), new Book(account, "Mednyj vsadnik", "Pushkin", "Book 2"));
        userService.remove(account.getId());
        assertThat(accountRepository.findAll()).hasSize(0);
        assertThat(bookRepository.findAll()).hasSize(0);
    }
}