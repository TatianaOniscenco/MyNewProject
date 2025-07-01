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
import enums.HttpMethod;

public class APISteps {
    private static final Logger log = LoggerFactory.getLogger(APISteps.class);
    private Response response;

    @Given("System is up and running")
    public void systemIsUpAndRunning() {
        log.info("[INFO] Assuming system is up");
    }

    @When("User searches for {string} via POST to SEARCH_PRODUCT endpoint")
    public void searchProduct(String productName) {
        log.info("[API][POST] Searching for product: {}", productName);
        response = ApiActions.postToSearchProduct(productName);
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
    public void userSendsRequestToEndpoint(String methodString, String endpointName) {
        HttpMethod method = HttpMethod.valueOf(methodString.toUpperCase());
        ApiEndpoint endpoint = ApiEndpoint.valueOf(endpointName.toUpperCase());

        log.info("[API][{}] Sending request to: {}", method, endpoint.getPath());
        response = ApiActions.sendRequest(endpoint, method);
    }

    @Then("Response code is {int}")
    public void responseCodeIs(int expectedCode) {
        int actualCode = response.statusCode();
        log.info("[ASSERT] Expecting response code: {} | Actual: {}", expectedCode, actualCode);
        assertEquals(expectedCode, actualCode, " Expected code: " + expectedCode + ", but got: " + actualCode);
    }

    @Then("Response message is {string}")
    public void responseMessageIs(String expectedMessage) {
        String actual = response.getBody().asString();
        log.info("[ASSERT] Verifying response contains message: \"{}\"", expectedMessage);
        assertTrue(actual.contains(expectedMessage),
                " Expected message: '" + expectedMessage + "'\nBut got: '" + actual + "'");
    }

    @Then("Response contains a list of products")
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

    @Then("Response contains a list of brands")
    public void responseContainsListOfBrands() {
        List<?> brands = response.jsonPath().getList("brands");

        log.info("[ASSERT] Validating brand list is not null or empty");
        assertNotNull(brands, " 'brands' key not found in response");
        assertFalse(brands.isEmpty(), " 'brands' list is empty");

        log.info("[INFO] Found {} brands", brands.size());
    }

    @Then("Response contains searched products list")
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
}