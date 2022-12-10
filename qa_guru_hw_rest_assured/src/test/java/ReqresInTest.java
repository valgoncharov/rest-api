import com.github.javafaker.Faker;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ReqresInTest {

    @Test
    void getListUsersTest() {
        given()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total_pages", is(2));


    }

    @Test
    void getSingleUsersTest() {
        given()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2));


    }

    @Test
    void getSingleUsersNotFoundTest() {
        given()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void getListResourceTest() {
        given()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("true red"));
    }

    @Test
    void postLoginTest400() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .log().uri()
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .log().status()
                .log().body()

                .assertThat().statusCode(400)
                .assertThat().body(containsString("Missing email or username"));
    }

    @Test
    void postLoginSuccessfulTest() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .log().status()
                .log().body()

                .assertThat().statusCode(200)
                .assertThat().body(containsString("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postLoginUnsuccessfulTest() {
        String data = "{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .log().status()
                .log().body()

                .assertThat().statusCode(400)
                .assertThat().body(containsString("Missing password"));
    }

    @Test
    void postCreateUserTest() {
        String data = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .log().status()
                .log().body()

                .assertThat().statusCode(201)
                .assertThat().body(containsString("id"));
    }

    @Test
    void postCreateUserWithFakerTest() {
        Faker faker = new Faker();
        String userLebowski = faker.lebowski().character();
        String userName = faker.name().name();

        given()
                .log().uri()
                .contentType(JSON)
                .body("{\"name\": \"" + userName + "\",\"character\": \"" + userLebowski + "\"}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .log().status()
                .log().body()

                .assertThat().statusCode(201)
                .assertThat().body(containsString(userLebowski))
                .assertThat().body(containsString(userName));
    }

    @Test
    @DisplayName("Delete user, this test you can run all time")
    void deleteUserSuccessfulTest() {

        given()
                .log().uri()
                .contentType(JSON)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .log().status()

                .assertThat().statusCode(204);
    }

    @Test
    @Disabled("Delete user, if you indicate exist user, you can run this test only once")
    void deleteUserTest() {

        given()
                .log().uri()
                .contentType(JSON)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .log().status()

                .assertThat().statusCode(204);
    }

}
