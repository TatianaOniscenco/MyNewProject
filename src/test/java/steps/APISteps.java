package steps;

import api.actions.ApiActions;
import api.dtos.responses.ProductListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.Hooks;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class APISteps {

    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        Hooks.logToFile("[INFO] Assuming system is up");
    }

    @When("User does GET call to {string} endpoint")
    public void userDoesGETCallToEndpoint(String endpoint) {
        Hooks.logToFile("[API][GET] Endpoint: " + endpoint);
        if (endpoint.contains("brands")) {
            response = ApiActions.getAllBrands();
        } else if (endpoint.contains("productsList")) {
            response = ApiActions.getAllProducts();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("User does POST call to {string} endpoint")
    public void userDoesPOSTCallToEndpoint(String endpoint) {
        Hooks.logToFile("[API][POST] Endpoint: " + endpoint);
        if (endpoint.contains("productsList")) {
            response = ApiActions.postToProductsList();
        } else if (endpoint.contains("searchProduct")) {
            response = ApiActions.postToSearchProduct();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @Then("Response code is {int}")
    public void responseCodeIs(int expectedCode) {
        int actualCode = response.statusCode();
        Hooks.logToFile("[ASSERT] Expecting response code: " + expectedCode + " | Actual: " + actualCode);
        assertEquals(expectedCode, actualCode,
                " Expected code: " + expectedCode + ", but got: " + actualCode);
    }

    @And("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        Hooks.logToFile("[ASSERT] Verifying response contains message: \"" + expectedMessage + "\"");
        assertTrue(actual.contains(expectedMessage),
                " Expected message: '" + expectedMessage + "'\nBut got: '" + actual + "'");
    }

    @And("Response contains a list of products")
    public void responseContainsListOfProducts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        Hooks.logToFile("[ASSERT] Validating product list is not null or empty");
        assertNotNull(productListResponse.products, " 'products' list is null");
        assertFalse(productListResponse.products.isEmpty(), " 'products' list is empty");

        Hooks.logToFile("[INFO] Parsed " + productListResponse.products.size() + " products");
        productListResponse.products.stream()
                .limit(3)
                .forEach(product -> Hooks.logToFile(" - " + product));
    }

    @And("Response contains a list of brands")
    public void responseContainsListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        Hooks.logToFile("[ASSERT] Validating brand list is not null or empty");
        assertNotNull(brands, " 'brands' key not found in response");
        assertFalse(brands.isEmpty(), " 'brands' list is empty");

        Hooks.logToFile("[INFO] Found " + brands.size() + " brands");
    }

    @When("User does PUT call to {string} endpoint")
    public void userDoesPUTCallToEndpoint(String endpoint) {
        Hooks.logToFile("[API][PUT] Endpoint: " + endpoint);
        if (endpoint.contains("brands")) {
            response = ApiActions.putAllBrands();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("User searches for {string} via POST to {string}")
    public void postSearchWithProduct(String productName, String endpoint) {
        Hooks.logToFile("[API][POST] Searching for product: " + productName + " | Endpoint: " + endpoint);
        if (endpoint.contains("searchProduct")) {
            response = ApiActions.postToSearchProduct(productName);
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @And("Response contains searched products list")
    public void responseContainsSearchedProductsList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        Hooks.logToFile("[ASSERT] Validating searched product list is not null or empty");
        assertNotNull(productListResponse.products, " 'products' list is null");
        assertFalse(productListResponse.products.isEmpty(), " 'products' list is empty");

        Hooks.logToFile("[INFO] Found " + productListResponse.products.size() + " matching products");
        productListResponse.products.stream()
                .limit(3)
                .forEach(product -> Hooks.logToFile(" - " + product));
    }

    @When("verify {string} and {string} via POST to {string}")
    public void verifyAndViaPOSTTo(String email, String password, String endpoint) {
        Hooks.logToFile("[API][POST] Verifying login with email: " + email + " | Endpoint: " + endpoint);
        if (endpoint.contains("verifyLogin")) {
            response = ApiActions.postToVerifyLogin(email, password);
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("verify {string} via POST to {string}")
    public void verifyViaPOSTTo(String password, String endpoint) {
        Hooks.logToFile("[API][POST] Verifying login with password only | Endpoint: " + endpoint);
        if (endpoint.contains("verifyLogin")) {
            response = ApiActions.postToVerifyLogin(password);
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("user does DELETE cal to {string} endpoint")
    public void userDoesDELETECalToEndpoint(String endpoint) {
        Hooks.logToFile("[API][DELETE] Endpoint: " + endpoint);
        if (endpoint.contains("verifyLogin")) {
            response = ApiActions.deleteToVerifyLogin();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }
}