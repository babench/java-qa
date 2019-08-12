package ru.otus.zaikin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users/{userId}/books")
public class BookRestController {
    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    List<Book> getBooks(@PathVariable Long userId) {
        return bookService.getBooks(userId);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = "application/json")
    Optional<Book> get(@PathVariable Long userId, @PathVariable Long bookId) {
        return bookService.getBook(userId, bookId);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    Book add(@PathVariable Long userId, @RequestBody Book book) {
        return bookService.add(userId, book);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable Long bookId) {
        bookService.remove(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    List<Book> put(@PathVariable Long userId, @RequestBody List<Book> books) {
        return bookService.put(userId, books);
    }
}