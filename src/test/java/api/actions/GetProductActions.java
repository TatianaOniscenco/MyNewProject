package api.actions;

import config.ConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class GetProductActions {

    private static final String baseUrl = ConfigReader.get("base.url");

    public static Response getAllProducts() {
        return given()
                .log().all()
                .when()
                .get(baseUrl + "/api/productsList")
                .then()
                .log().body()
                .extract().response();
    }

    public static Response getAllBrands() {
        return given()
                .log().all()
                .when()
                .get(baseUrl + "/api/brandsList")
                .then()
                .log().body()
                .extract().response();
    }

    public static Response postToProductsList() {
        return given()
                .log().all()
                .when()
                .post(baseUrl + "/api/productsList")
                .then()
                .log().body()
                .extract().response();
    }
}