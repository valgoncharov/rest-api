package owner;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;
import owner.config.AuthConfig;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;

public class AuthTest {

    @Test
    void postLoginSuccessfulTest() {
        AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());
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
}
