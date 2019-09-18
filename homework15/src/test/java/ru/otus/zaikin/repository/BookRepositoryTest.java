package ru.otus.zaikin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.otus.zaikin.config.AppConfig;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.services.BookService;
import ru.otus.zaikin.services.UserService;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = {AppConfig.class, UserService.class, BookService.class})
public class BookRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Test
    public void testFindByAccount() {
        Account account = userService.add(new Account("Zaikin", "password"));
        Book book1 = bookService.add(account.getId(), new Book(account, "Onegin", "Pushkin", "Book 1"));
        Book book2 = bookService.add(account.getId(), new Book(account, "Mednyj vsadnik", "Pushkin", "Book 2"));
        List<Book> books = bookRepository.findByAccount(account);
        assertNotNull(books);
        assertEquals(books.size(), 2);
    }
}