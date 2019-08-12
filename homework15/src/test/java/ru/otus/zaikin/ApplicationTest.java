package ru.otus.zaikin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.entity.Account;
import ru.otus.zaikin.entity.Book;
import ru.otus.zaikin.repository.AccountRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class ApplicationTest extends AbstractTestNGSpringContextTests {
    private Account account;

    @LocalServerPort
    private int port;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test(description = "should create User")
    public void shouldCreateUser() throws JsonProcessingException {
        log.debug("shouldCreateUser");
        account = new Account("Bob", "password");
        ObjectMapper mapper = new ObjectMapper();
        String url = "/users";
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(account));
        Response response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        account = response.getBody().as(Account.class);
        log.debug(account);
    }

    @Test(description = "should create Book", dependsOnMethods = "shouldCreateUser", invocationCount = 5)
    public void shouldCreateBook() throws JsonProcessingException {
        log.debug("shouldCreateBook");
        assertThat(account).isNotNull();
        assertThat(account.getId())
                .isNotNull()
                .isGreaterThan(0L);
        String url = "/users/" + account.getId() + "/books";
        Book book = new Book(account, "Anna Karenina", "Leo Tolstoy", "description");
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(book));
        Response response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
    }

    @Test(description = "should put Book", dependsOnMethods = "shouldCreateBook")
    public void shouldPutBook() throws JsonProcessingException {
        log.debug("shouldPutBook");
        String url = "/users/" + account.getId() + "/books";
        Book book1 = new Book(account, "Anna Karenina 1", "Leo Tolstoy", "description");
        Set<Book> books = new HashSet<>();
        books.add(book1);
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(books));
        Response response = requestSpecification.put(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        assertThat(accountRepository.findById(account.getId()).get().getBooks()).hasSize(1);
        log.debug("here");
    }

    @Test(description = "should delete Book", dependsOnMethods = "shouldPutBook")
    public void shouldDeleteBook() throws JSONException {
        log.debug("shouldDeleteBook");
        List<Book> books = accountRepository.findById(account.getId()).get().getBooks();
        books.forEach(book -> {
            RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
            Response response = requestSpecification.delete("/users/" + account.getId() + "/books/" + book.getId());
            response.prettyPrint();
            response.then()
                    .statusCode(SC_NO_CONTENT)
                    .log();
        });
    }

    @Test(description = "should delete User", dependsOnMethods = "shouldDeleteBook")
    public void shouldDeleteUser() {
        log.debug("shouldDeleteUser");
        String url = "/users/" + account.getId();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        Response response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_NO_CONTENT)
                .log();
    }
}