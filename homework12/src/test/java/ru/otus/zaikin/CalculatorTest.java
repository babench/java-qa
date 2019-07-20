package ru.otus.zaikin;

import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.web.CalculatorOperation;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorTest extends AbstractTestNGSpringContextTests {
    @LocalServerPort
    private int port;

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test(description = "should perform Plus operation")
    void shouldPlus() {
        CalculatorOperation operation = CalculatorOperation.PLUS;
        int result = RestAssured.given()
                .param("operation", operation)
                .param("a", 5)
                .param("b", 3)
                .when().get("/calc").then().statusCode(200)
                .assertThat().log().all()
                .extract().body().as(Integer.class);
        assertThat(result).isEqualTo(8);
    }

    @Test(description = "should perform Minus operation")
    void shouldMinus() {
        CalculatorOperation operation = CalculatorOperation.MINUS;
        int result = RestAssured.given()
                .param("operation", operation)
                .param("a", 10)
                .param("b", 3)
                .when().get("/calc").then().statusCode(200)
                .assertThat().log().all()
                .extract().body().as(Integer.class);
        assertThat(result).isEqualTo(7);
    }

    @Test(description = "should perform Multiply operation")
    void shouldMultiply() {
        CalculatorOperation operation = CalculatorOperation.MULTIPLY;
        int result = RestAssured.given()
                .param("operation", operation)
                .param("a", 5)
                .param("b", 3)
                .when().get("/calc").then().statusCode(200)
                .assertThat().log().all()
                .extract().body().as(Integer.class);
        assertThat(result).isEqualTo(15);
    }

    @Test(description = "should perform Divide operation")
    void shouldDivide() {
        CalculatorOperation operation = CalculatorOperation.DIVIDE;
        int result = RestAssured.given()
                .param("operation", operation)
                .param("a", 100)
                .param("b", 2)
                .when().get("/calc").then().statusCode(200)
                .assertThat().log().all()
                .extract().body().as(Integer.class);
        assertThat(result).isEqualTo(50);
    }

    @Test(description = "should not perform Divide operation")
    void shouldNotDivide() {
        CalculatorOperation operation = CalculatorOperation.DIVIDE;
        RestAssured.given()
                .param("operation", operation)
                .param("a", 100)
                .param("b", 0)
                .when().get("/calc").then().statusCode(500)
                .assertThat().log().all();
    }
}