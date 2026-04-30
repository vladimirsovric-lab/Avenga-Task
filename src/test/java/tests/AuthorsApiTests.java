package tests;

import config.TestConfig;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import utils.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthorsApiTests {
    private static final String BASE_PATH = "/api/v1/Authors";
    private static final String VALID_ID = "1";
    private static final String NON_EXISTING_ID = "99999999";
    private static final String INVALID_ID = "abc";

    private RequestSpecification request() {
        return given()
                .baseUri(TestConfig.BASE_URL)
                .contentType("application/json");
    }

    @Test
    void shouldGetAllAuthors() {
        request()
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void shouldGetAuthorByValidId() {
        request()
                .when()
                .get(BASE_PATH + "/" + VALID_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void shouldReturn404ForNonExistingAuthor() {
        request()
                .when()
                .get(BASE_PATH + "/" + NON_EXISTING_ID)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturn400ForInvalidAuthorId() {
        request()
                .when()
                .get(BASE_PATH + "/" + INVALID_ID)
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }

    @Test
    void shouldCreateAuthorWithValidPayload() {
        request()
                .body(TestData.validAuthorPayload())
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body("id", equalTo(101));
    }

    @Test
    void shouldReturnErrorWhenCreatingAuthorWithInvalidPayload() {
        request()
                .body(TestData.invalidAuthorPayload())
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    void shouldUpdateAuthorWithValidPayload() {
        request()
                .body(TestData.validAuthorPayload())
                .when()
                .put(BASE_PATH + "/" + VALID_ID)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn200WhenUpdatingNonExistingAuthor_dueToMockApiBehavior() {
        request()
                .body(TestData.validAuthorPayload())
                .when()
                .put(BASE_PATH + "/" + NON_EXISTING_ID)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldDeleteAuthorWithValidId() {
        request()
                .when()
                .delete(BASE_PATH + "/" + VALID_ID)
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void shouldReturnErrorWhenDeletingWithInvalidId() {
        request()
                .when()
                .delete(BASE_PATH + "/" + INVALID_ID)
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }
}
