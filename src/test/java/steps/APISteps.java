package steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

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
    public void userDoesGETCallToEndpoint(String url) {
        response = given()
                .log().all()                // logs the full request
                .when()
                .get(url)
                .then()
                .log().body()              // logs the full response body
                .extract()
                .response();
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
