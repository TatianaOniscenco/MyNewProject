package steps;

import ENUM.ApiEndpoint;
import api.actions.ApiActions;
import api.dtos.responses.ProductListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class APISteps {
    private static final Logger log = LoggerFactory.getLogger(APISteps.class);

    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        log.info("[INFO] Assuming system is up");
    }

    @When("User does GET call to {string} endpoint")
    public void userDoesGETCallToEndpoint(String endpoint) {
        log.info("[API][GET] Endpoint: {}", endpoint);
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
        log.info("[API][POST] Endpoint: {}", endpoint);
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
        log.info("[ASSERT] Expecting response code: {} | Actual: {}", expectedCode, actualCode);
        assertEquals(expectedCode, actualCode, " Expected code: " + expectedCode + ", but got: " + actualCode);
    }

    @And("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        log.info("[ASSERT] Verifying response contains message: \"{}\"", expectedMessage);
        assertTrue(actual.contains(expectedMessage),
                " Expected message: '" + expectedMessage + "'\nBut got: '" + actual + "'");
    }

    @And("Response contains a list of products")
    public void responseContainsListOfProducts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        log.info("[ASSERT] Validating product list is not null or empty");
        assertNotNull(productListResponse.products, " 'products' list is null");
        assertFalse(productListResponse.products.isEmpty(), " 'products' list is empty");

        log.info("[INFO] Parsed {} products", productListResponse.products.size());
        productListResponse.products.stream()
                .limit(3)
                .forEach(product -> log.info(" - {}", product));
    }

    @And("Response contains a list of brands")
    public void responseContainsListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        log.info("[ASSERT] Validating brand list is not null or empty");
        assertNotNull(brands, " 'brands' key not found in response");
        assertFalse(brands.isEmpty(), " 'brands' list is empty");

        log.info("[INFO] Found {} brands", brands.size());
    }

    @When("User does PUT call to {string} endpoint")
    public void userDoesPUTCallToEndpoint(String endpoint) {
        log.info("[API][PUT] Endpoint: {}", endpoint);
        if (endpoint.contains("brands")) {
            response = ApiActions.putAllBrands();
        } else {
            throw new IllegalArgumentException("Unsupported endpoint: " + endpoint);
        }
    }

    @When("User searches for {string} via POST to SEARCH_PRODUCT endpoint")
    public void searchProduct(String productName) {
        log.info("[API][POST] Searching for product: {}", productName);
        response = ApiActions.postToSearchProduct(productName);
    }

    @And("Response contains searched products list")
    public void responseContainsSearchedProductsList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        log.info("[ASSERT] Validating searched product list is not null or empty");
        assertNotNull(productListResponse.products, " 'products' list is null");
        assertFalse(productListResponse.products.isEmpty(), " 'products' list is empty");

        log.info("[INFO] Found {} matching products", productListResponse.products.size());
        productListResponse.products.stream()
                .limit(3)
                .forEach(product -> log.info(" - {}", product));
    }

    @When("verify login with {string} and {string}")
    public void verifyLoginWithConfigValues(String emailKey, String passwordKey) {
        String email = ConfigReader.get(emailKey.replace(" ", "."));
        String password = ConfigReader.get(passwordKey.replace(" ", "."));
        log.info("[API][POST] Verifying login with valid email and password from config: {}, {}", email, password);
        response = ApiActions.postToVerifyLogin(email, password);
    }

    @When("verify login with only {string}")
    public void verifyLoginWithOnlyPassword(String passwordKey) {
        String password = ConfigReader.get(passwordKey.replace(" ", "."));
        log.info("[API][POST] Verifying login with password only from config: {}", password);
        response = ApiActions.postToVerifyLogin(password);
    }

    @When("User sends {string} request to {string} endpoint")
    public void userSendsRequestToEndpoint(String method, String endpointName) {
        ApiEndpoint endpoint = ApiEndpoint.valueOf(endpointName.toUpperCase());
        log.info("[API][{}] Sending request to: {}", method, endpoint);
        response = ApiActions.sendRequest(endpoint, method);
    }
}