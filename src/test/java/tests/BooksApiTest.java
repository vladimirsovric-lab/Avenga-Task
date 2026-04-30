package tests;

import config.TestConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import utils.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class BooksApiTest {

    private static final String BASE_PATH = "/api/v1/Books";
    private static final String VALID_ID = "1";
    private static final String INVALID_ID = "invalid_id";
    private static final String NON_EXISTING_ID = "123456789";

    private RequestSpecification request() {
        return given()
                .baseUri(TestConfig.BASE_URL)
                .contentType("application/json");
    }

    @Test
    @Feature("Books API")
    @Description("Verify getting all books returns non-empty list")
    void testGetAllBooks() {
        request()
            .when()
                .get(BASE_PATH)
            .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
    }

    @Test
    void testGetBookByValidId() {
        request()
            .when()
                .get(BASE_PATH + "/" + VALID_ID)
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyOrNullString()));
    }

    @Test
    void testReturnStatus404ForNonExistingBook() {
        request()
            .when()
                .get(BASE_PATH + "/" + NON_EXISTING_ID)
            .then()
                .statusCode(404);
    }

    @Test
    void testReturnStatus400ForInvalidFormat() {
        request()
            .when()
                .get(BASE_PATH + "/" + INVALID_ID)
            .then()
                .statusCode(anyOf(is(400), is(404))); // depending on how API may handle the (invalid) input
    }

    @Test
    void testCreateBookWithValidPayload() {
        request()
                .body(TestData.validBookPayload())
        .when()
                .post(BASE_PATH)
        .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("title", equalTo("Avenga Task Vladimir Sovric"))
                .body("pageCount", equalTo(555));
    }

    @Test
    void testReturnStatus400CreatingBookWithInvalidPayload() {
        request()
                .body(TestData.invalidBookPayload())
        .when()
                .post(BASE_PATH)
        .then()
                .statusCode(anyOf(is(400), is(415), is(500)));
    }

    @Test
    void testReturnStatus400CreatingBookWithEmptyBody() {
        request()
                .body("")
        .when()
                .post(BASE_PATH)
        .then()
                .statusCode(anyOf(is(400), is(415)));
    }

    @Test
    void testUpdateBookWithValidPayload() {
        request()
                .body(TestData.validBookPayload())
        .when()
                .put(BASE_PATH + "/" + VALID_ID)
        .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("title", equalTo("Avenga Task Vladimir Sovric"));
    }


    @Test
    void testReturnStatus200WhenUpdatingNonExistingObjectMockApiBehavior() {
        request()
                .body(TestData.validBookPayload())
        .when()
                .put(BASE_PATH + "/" + NON_EXISTING_ID)
        .then()
                //.statusCode(anyOf(is(404), is(400)));
                .statusCode(equalTo(200));// FakeRestAPI is not validating resources existence, returning 200 OK for not found resources, real life most likely returns 404
    }

    @Test
    void testReturnStatus400WhenUpdatingWithInvalidPayload() {
        request()
                .body(TestData.invalidBookPayload())
        .when()
                .put(BASE_PATH + "/" + VALID_ID)
        .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    void testReturnStatus400WhenUpdatingWithEmptyBody() {
        request()
                .body("")
        .when()
                .baseUri(BASE_PATH + "/" + VALID_ID)
        .then()
                .statusCode(anyOf(is(400), is(415)));
    }

    @Test
    void shouldDeleteBookWithValidId() {
        request()
        .when()
                .delete(BASE_PATH + "/" + VALID_ID)
        .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void shouldReturnStatus404WhenDeletingNonExistingBook() {
        request()
        .when()
                .delete(BASE_PATH + "/" + NON_EXISTING_ID)
        .then()
                .statusCode(anyOf(is(404), is (200)));
    }

    @Test
    void shouldReturnStatus400WhenDeletingWithInvalidId() {
        request()
        .when()
                .delete(BASE_PATH + "/" + INVALID_ID)
        .then()
                .statusCode(anyOf(is(400), is(404)));
    }

}