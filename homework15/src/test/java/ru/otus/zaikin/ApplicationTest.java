package ru.otus.zaikin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.repository.AccountRepository;
import ru.otus.zaikin.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class ApplicationTest extends AbstractTestNGSpringContextTests {
    @LocalServerPort
    private int port;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private Response createAccount(Account account) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(account));
        return requestSpecification.post("/users");
    }

    private Response createBook(Book book) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(book));
        return requestSpecification.post("/users/{accountId}/books", book.getAccount().getId());
    }

    private Response putBooks(Set<Book> books) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(books));
        Optional<Book> optionalBook = books.stream().findFirst();
        Book book1 = optionalBook.orElseThrow(() -> new RuntimeException("No books"));
        return requestSpecification.put("/users/{accountId}/books", book1.getAccount().getId());
    }

    private Response deleteBook(Book book) {
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        return requestSpecification.delete("/users/{accountId}/books/{bookId}", book.getAccount().getId(), book.getId());
    }

    @Test(description = "should create User")
    public void shouldCreateUser() throws JsonProcessingException {
        log.debug("shouldCreateUser");
        Response response = createAccount(new Account("Petya", "password"));
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        Account account = response.getBody().as(Account.class);
        assertThat(account).isNotNull();
        assertThat(account.getId())
                .isNotNull()
                .isGreaterThan(0L);
        assertThat(account.getUsername()).isEqualTo("Petya");
    }

    @Test(description = "should create Book")
    public void shouldCreateBook() throws JsonProcessingException {
        log.debug("shouldCreateBook");
        Account account = createAccount(new Account("Vasya", "password")).getBody().as(Account.class);

        Response response = createBook(new Book(account, "Anna Karenina", "Leo Tolstoy", "description"));
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        Book book = response.getBody().as(Book.class);
        List<Book> booksByAccount = bookRepository.findByAccount(account);
        assertThat(booksByAccount).isNotNull().isNotEmpty().hasSize(1).contains(book);
    }

    @Test(description = "should put Book")
    public void shouldPutBook() throws JsonProcessingException {
        log.debug("shouldPutBook");
        Account account = createAccount(new Account("Ivan", "password")).getBody().as(Account.class);
        Book book1 = new Book(account, "Anna Karenina", "Leo Tolstoy", "description");
        createBook(book1);
        Book book2 = new Book(account, "Anna Karenina 2", "Leo Tolstoy", "description");
        createBook(book2);

        Book book = new Book(account, "Anna Karenina 3", "Leo Tolstoy", "description");
        Set<Book> books = new HashSet<>();
        books.add(book);
        Response response = putBooks(books);

        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        List<Book> bookList = accountRepository.findById(account.getId()).get().getBooks();
        assertThat(bookList).hasSize(1);
        assertThat(bookList.get(0).getName()).isEqualTo(book.getName());
    }


    @Test(description = "should delete Book")
    public void shouldDeleteBook() throws JsonProcessingException {
        log.debug("shouldDeleteBook");
        Account account = createAccount(new Account("Olga", "password")).getBody().as(Account.class);
        createBook(new Book(account, "Anna Karenina", "Leo Tolstoy", "description"));
        List<Book> books = accountRepository.findById(account.getId()).get().getBooks();

        books.forEach(book -> {
            Response response = deleteBook(book);
            response.prettyPrint();
            response.then()
                    .statusCode(SC_NO_CONTENT)
                    .log();
        });
        List<Book> booksByAccount = bookRepository.findByAccount(account);
        assertThat(booksByAccount).isEmpty();
    }


    @Test(description = "should delete User")
    public void shouldDeleteUser() throws JsonProcessingException {
        log.debug("shouldDeleteUser");
        Account account = createAccount(new Account("Sveta", "password")).getBody().as(Account.class);
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        Response response = requestSpecification.delete("/users/{accountId}", account.getId());
        response.prettyPrint();
        response.then()
                .statusCode(SC_NO_CONTENT)
                .log();
        assertThat(accountRepository.findByUsername(account.getUsername())).isNotPresent();
    }
}