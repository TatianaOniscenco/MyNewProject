package steps;

import api.actions.ProductApi;
import api.dtos.responses.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class APISteps {

    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        System.out.println("System is assumed to be up.");
    }

    @When("User does GET call to {string} endpoint")
    public void userDoesGETCallToEndpoint(String endpoint) {
        if (endpoint.contains("brands")) {
            response = ProductApi.getAllBrands();
        } else if (endpoint.contains("productsList")) {
            response = ProductApi.getAllProducts();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("User does POST call to {string} endpoint")
    public void userDoesPOSTCallToEndpoint(String endpoint) {
        if (endpoint.contains("productsList")) {
            response = ProductApi.postToProductsList();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @Then("Response code is {int}")
    public void responseCodeIs(int expectedCode) {
        int actualCode = response.statusCode();
        assertEquals(expectedCode, actualCode,
                " Expected code: " + expectedCode + ", but got: " + actualCode);
    }

    @And("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        assertTrue(actual.contains(expectedMessage),
                " Expected message: '" + expectedMessage + "'\nBut got: '" + actual + "'");
    }

    @And("Response contains a list of products")
    public void responseContainsListOfProducts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductResponse productResponse = mapper.readValue(response.asString(), ProductResponse.class);

        assertNotNull(productResponse.products, " 'products' list is null");
        assertFalse(productResponse.products.isEmpty(), " 'products' list is empty");

        System.out.println(" Parsed " + productResponse.products.size() + " products:");
        productResponse.products.stream()
                .limit(3)
                .forEach(product -> System.out.println(" - " + product));
    }

    @And("Response contains a list of brands")
    public void responseContainsListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        assertNotNull(brands, " 'brands' key not found in response");
        assertFalse(brands.isEmpty(), " 'brands' list is empty");

        System.out.println(" Found " + brands.size() + " brands.");
    }

    @When("User does PUT call to {string} endpoint")
    public void userDoesPUTCallToEndpoint(String endpoint) {
        if (endpoint.contains("brands")) {
            response = ProductApi.putAllBrands();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("User searches for {string} via POST to {string}")
    public void postSearchWithProduct(String productName, String endpoint) {
        if (endpoint.contains("searchProduct")) {
            response = ProductApi.postToSearchProduct(productName);
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }

    }

    @And("Response contains searched products list")
    public void responseContainsSearchedProductsList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductResponse productResponse = mapper.readValue(response.asString(), ProductResponse.class);

        assertNotNull(productResponse.products, " 'products' list is null");
        assertFalse(productResponse.products.isEmpty(), " 'products' list is empty");

        System.out.println(" Found " + productResponse.products.size() + " matching products:");
        productResponse.products.stream()
                .limit(3)
                .forEach(product -> System.out.println(" - " + product));
    }


}