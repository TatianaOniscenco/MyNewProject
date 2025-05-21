package steps;

import config.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class APISteps {

    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        // Optional: ping or log
        System.out.println("🟢 System is assumed to be up.");
    }

    @When("User does GET call to {string} endpoint")
    public void userDoesGETCallToEndpoint(String endpoint) {
        String baseUrl = ConfigReader.get("base.url");

        response = given()
                .log().all()
                .when()
                .get(baseUrl + endpoint)
                .then()
                .log().body()
                .extract()
                .response();
    }

    @When("User does POST call to {string} endpoint")
    public void userDoesPOSTCallToEndpoint(String endpoint) {
        String baseUrl = ConfigReader.get("base.url");

        response = given()
                .log().all()                // logs the full request
                .when()
                .post(baseUrl + endpoint)
                .then()
                .log().body()              // logs the full response body
                .extract()
                .response();
    }
    @And("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        assertTrue(actual.contains(expectedMessage),
                "Expected message: " + expectedMessage + "\nBut got: " + actual);
    }
    @And("Response contains a list of brands")
    public void responseContainsAListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        assertNotNull(brands, "❌ 'brands' key is missing in the response");
        assertFalse(brands.isEmpty(), "❌ 'brands' list is empty");

        System.out.println("✅ Found " + brands.size() + " brands.");
    }
    @Then("Response code is {int}")
    public void responseCodeIs(int statusCode) {
        assertEquals(statusCode, response.getStatusCode(),
                "❌ Expected status " + statusCode + " but got " + response.getStatusCode());
    }

    @And("Response contains a list of products")
    public void responseContainsListOfProducts() {
        assertNotNull(response.jsonPath().getList("products"),
                "❌ No 'products' key found in response");
        assertTrue(response.jsonPath().getList("products").size() > 0,
                "❌ 'products' list is empty");
    }
}
