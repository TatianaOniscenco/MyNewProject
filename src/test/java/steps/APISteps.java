package steps;

import ENUM.ApiEndpoint;
import api.actions.ApiActions;
import api.dtos.responses.ProductListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigReader;
import ENUM.HttpMethod;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class APISteps {

    private final Logger log = LoggerFactory.getLogger(APISteps.class);
    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        log.info("[INFO] Assuming system is up");
    }

    @When("User searches for {string} via POST to SEARCH_PRODUCT endpoint")
    public void searchProduct(String productName) {
        response = ApiActions.postToSearchProduct(productName);
        log.info("[API][POST] Searching for product: {}", productName);
    }

    @When("verify login with {string} and {string}")
    public void verifyLoginWithConfigValues(String emailKey, String passwordKey) {
        String email = ConfigReader.get(emailKey.replace(" ", "."));
        String password = ConfigReader.get(passwordKey.replace(" ", "."));
        response = ApiActions.postToVerifyLogin(email, password);
        log.info("[API][POST] Verifying login with valid email and password from config: {}, {}", email, password);
    }

    @When("verify login with only {string}")
    public void verifyLoginWithOnlyPassword(String passwordKey) {
        String password = ConfigReader.get(passwordKey.replace(" ", "."));
        response = ApiActions.postToVerifyLogin(password);
        log.info("[API][POST] Verifying login with password only from config: {}", password);
    }

    @When("User sends {string} request to {string} endpoint")
    public void userSendsRequestToEndpoint(String methodString, String endpointName) {
        HttpMethod method = HttpMethod.valueOf(methodString.toUpperCase());
        ApiEndpoint endpoint = ApiEndpoint.valueOf(endpointName.toUpperCase());

        response = ApiActions.sendRequest(endpoint, method);
        log.info("[API][{}] Sending request to: {}", method, endpoint.getPath());
    }

    @Then("Response code is {int}")
    public void responseCodeIs(int expectedCode) {
        int actualCode = response.statusCode();

        if (actualCode == expectedCode) {
            log.info("[ASSERT] Status code matched: {}", expectedCode);
        } else {
            log.error("[ASSERT][FAIL] Expected status code: {}, but got: {}", expectedCode, actualCode);
            log.debug("[RESPONSE BODY] {}", response.asPrettyString());
            assertEquals(expectedCode, actualCode,
                    String.format("Expected code: %d, but got: %d", expectedCode, actualCode));
        }
    }

    @Then("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        if (actual.contains(expectedMessage)) {
            log.info("[ASSERT] Response contains expected message: '{}'", expectedMessage);
        } else {
            log.error("[ASSERT][FAIL] Expected message not found.\nExpected: '{}'\nActual: '{}'", expectedMessage, actual);
            assertTrue(actual.contains(expectedMessage),
                    "Expected message: '" + expectedMessage + "'\nBut got: '" + actual + "'");
        }
    }

    @Then("Response contains a list of products")
    public void responseContainsListOfProducts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        if (productListResponse.products == null) {
            log.error("[ASSERT][FAIL] Product list is null.\nResponse: {}", response.asPrettyString());
            assertNotNull(productListResponse.products, "'products' list is null");
            return;
        }

        if (productListResponse.products.isEmpty()) {
            log.error("[ASSERT][FAIL] Product list is empty.\nResponse: {}", response.asPrettyString());
            assertFalse(productListResponse.products.isEmpty(), "'products' list is empty");
            return;
        }

        log.info("[ASSERT] Parsed {} products", productListResponse.products.size());
        productListResponse.products.stream().limit(3).forEach(p -> log.info(" - {}", p));
    }

    @Then("Response contains a list of brands")
    public void responseContainsListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        if (brands != null && !brands.isEmpty()) {
            log.info("[ASSERT] Found {} brands", brands.size());
            brands.stream().limit(3).forEach(b -> log.debug(" - {}", b));
        } else {
            log.error("[ASSERT][FAIL] Brands list is missing or empty.\nResponse: {}", response.asPrettyString());
            assertNotNull(brands, "'brands' key not found in response");
            assertFalse(brands.isEmpty(), "'brands' list is empty");
        }
    }

    @Then("Response contains searched products list")
    public void responseContainsSearchedProductsList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductListResponse productListResponse = mapper.readValue(response.asString(), ProductListResponse.class);

        if (productListResponse.products != null && !productListResponse.products.isEmpty()) {
            log.info("[ASSERT] Found {} matching products", productListResponse.products.size());
            productListResponse.products.stream().limit(3).forEach(p -> log.debug(" - {}", p));
        } else {
            log.error("[ASSERT][FAIL] Searched product list is missing or empty.\nResponse: {}", response.asPrettyString());

            assertNotNull(productListResponse.products, "'products' list is null");
            assertFalse(productListResponse.products == null || productListResponse.products.isEmpty(),
                    "'products' list is empty");
        }
    }
}