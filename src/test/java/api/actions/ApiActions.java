package api.actions;
import ENUM.ApiEndpoint;
import config.ConfigReader;
import io.restassured.response.Response;
import static ENUM.ApiEndpoint.*;
import static io.restassured.RestAssured.*;
import enums.HttpMethod;

public class ApiActions {

    private static final String baseUrl = ConfigReader.get("base.url");

    public static Response sendRequest(ApiEndpoint endpoint, HttpMethod method) {
        String url = ConfigReader.get("base.url") + endpoint.getPath();

        switch (method) {
            case GET:
                return given()
                        .log().all()
                        .when()
                        .get(url)
                        .then()
                        .log().body()
                        .extract().response();

            case POST:
                return given()
                        .log().all()
                        .contentType("application/x-www-form-urlencoded")
                        .when()
                        .post(url)
                        .then()
                        .log().body()
                        .extract().response();

            case PUT:
                return given()
                        .log().all()
                        .contentType("application/x-www-form-urlencoded")
                        .when()
                        .put(url)
                        .then()
                        .log().body()
                        .extract().response();

            case DELETE:
                return given()
                        .log().all()
                        .contentType("application/x-www-form-urlencoded")
                        .when()
                        .delete(url)
                        .then()
                        .log().body()
                        .extract().response();

            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    public static Response postToSearchProduct(String productName) {
        return given()
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", productName)
                .when()
                .post(baseUrl + SEARCH_PRODUCT.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }

    public static Response postToVerifyLogin(String email, String password) {
        return given()
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(baseUrl + VERIFY_LOGIN.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }

    public static Response postToVerifyLogin(String password) {
        return given()
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("password", password)
                .when()
                .post(baseUrl + VERIFY_LOGIN.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }
}