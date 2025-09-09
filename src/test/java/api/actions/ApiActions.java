package api.actions;

import ENUM.ApiEndpoint;
import config.ConfigReader;
import io.restassured.response.Response;
import static ENUM.ApiEndpoint.*;
import static io.restassured.RestAssured.*;
import ENUM.HttpMethod;

public class ApiActions {

    private static final String BASE_URL = ConfigReader.getInstance().get("base.url");

    // Sends a request to the specified API endpoint using the given HTTP method.
    // Request body may be included (could be added later).
    public static Response sendRequest(ApiEndpoint endpoint, HttpMethod method) {
        String url = ConfigReader.getInstance().get("base.url") + endpoint.getPath();

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
                        .contentType(ConfigReader.getInstance().get("content.type"))
                        .when()
                        .post(url)
                        .then()
                        .log().body()
                        .extract().response();

            case PUT:
                return given()
                        .log().all()
                        .contentType(ConfigReader.getInstance().get("content.type"))
                        .when()
                        .put(url)
                        .then()
                        .log().body()
                        .extract().response();

            case DELETE:
                return given()
                        .log().all()
                        .contentType(ConfigReader.getInstance().get("content.type"))
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
                .contentType(ConfigReader.getInstance().get("content.type"))
                .formParam("search_product", productName)
                .when()
                .post(BASE_URL + SEARCH_PRODUCT.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }

    public static Response postToVerifyLogin(String email, String password) {
        return given()
                .log().all()
                .contentType(ConfigReader.getInstance().get("content.type"))
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(BASE_URL + VERIFY_LOGIN.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }

    // Overloaded method
    public static Response postToVerifyLogin(String password) {
        return given()
                .log().all()
                .contentType(ConfigReader.getInstance().get("content.type"))
                .formParam("password", password)
                .when()
                .post(BASE_URL + VERIFY_LOGIN.getPath())
                .then()
                .log().body()
                .extract()
                .response();
    }
}