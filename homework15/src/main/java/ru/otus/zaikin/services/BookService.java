package ru.otus.zaikin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.ex.UserNotFoundException;
import ru.otus.zaikin.repository.AccountRepository;
import ru.otus.zaikin.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AccountRepository accountRepository;


    public List<Book> getBooks(Long userId) {
        validateUser(userId);
        Account account = accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(Long.toString(userId)));
        List<Book> books = account.getBooks();
        return books;
    }

    public Optional<Book> getBook(Long userId, Long bookId) {
        validateUser(userId);
        return bookRepository.findById(bookId);
    }

    public Book add(Long userId, Book book) {
        validateUser(userId);
        Account account1 = accountRepository
                .findById(userId).orElseThrow(() -> new UserNotFoundException(Long.toString(userId)));
        return bookRepository.save(new Book(account1, book.name, book.author, book.description));
    }

    public void remove(Long bookId) {
        Book book = bookRepository.getOne(bookId);
        Account account = book.getAccount();
        account.getBooks().remove(book);
        accountRepository.save(account);
    }

    public void remove(Book book) {
        Account account = book.getAccount();
        account.getBooks().remove(book);
        accountRepository.save(account);
        bookRepository.delete(book);
    }


    private void validateUser(Long userId) {
        accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(Long.toString(userId)));
    }

    public List<Book> put(Long userId, List<Book> books) {
        Account account = accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(Long.toString(userId)));
        account.getBooks().removeAll(account.getBooks());
        account = accountRepository.save(account);
        for (Book book : books) bookRepository.save(new Book(account, book.name, book.author, book.description));
        List<Book> booksNew = accountRepository.findById(userId).get().getBooks();
        return booksNew;
    }

    @Transactional
    public void remove(Account account, Long bookId) {
        account.getBooks().remove(bookRepository.getOne(bookId));
        accountRepository.save(account);
    }
}