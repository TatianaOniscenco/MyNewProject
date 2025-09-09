package steps;

import com.microsoft.playwright.Page;
import context.ScenarioContext;
import context.ScenarioContextManager;
import context.UserContext;
import ENUM.SignupCountry;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SignupPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSteps {

    private final Logger log = LoggerFactory.getLogger(SignupSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final SignupPage signupPage = new SignupPage(page);
    private final ScenarioContext context = ScenarioContextManager.get();
    private final Faker faker = new Faker();

    @When("User enters valid account information")
    public void enterValidUserInformation() {
        UserContext user = context.get("user");

        // Generate remaining info
        String password = faker.internet().password();
        String address = faker.address().streetAddress();
        String state = faker.address().state();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        SignupCountry country = SignupCountry.getRandom();

        // Fill form
        signupPage.enterPassword(password);
        signupPage.enterFirstName(user.getFirstName());
        signupPage.enterLastName(user.getLastName());
        signupPage.enterAddress(address);
        signupPage.selectCountry(country.getLabel());
        signupPage.enterState(state);
        signupPage.enterCity(city);
        signupPage.enterZipCode(zipCode);
        signupPage.enterMobileNumber(phoneNumber);

        // Update context
        user.setPassword(password);
        user.setAddress(address);
        user.setCountry(country.getLabel());
        user.setState(state);
        user.setCity(city);
        user.setZipCode(zipCode);
        user.setPhoneNumber(phoneNumber);
        context.set("user", user);

        log.info("[DATA] Final UserContext: {}", user);
    }

    @When("User submits the signup form clicking on Create Account button")
    public void submitSignupForm() {
        signupPage.clickCreateAccountButton();
        log.info("[ACTION] Clicking 'Create Account' button");
    }

    @Then("user is redirected to Signup page")
    public void isRedirectedToSignupPage() {
        boolean isVisible = signupPage.isEnterAccountInfoVisible();
        if (isVisible) {
            log.info("[ASSERT] 'Enter Account Information' section is visible.");
        } else {
            log.error("[ASSERT][FAIL] 'Enter Account Information' heading is NOT visible.");
            assertTrue(false, "'Enter Account Information' heading is not visible");
        }
    }

    @Then("System displays the {string} message for existing user")
    public void systemDisplaysTheMessageForExistingUser(String existingUserMessage) {
        String actualMessage = signupPage.getExistingUserMessage();
        if (actualMessage.equals(existingUserMessage)) {
            log.info("[ASSERT] Existing user message matched: '{}'", actualMessage);
        } else {
            log.error("[ASSERT][FAIL] Existing user message mismatch — Expected: '{}', Actual: '{}'",
                    existingUserMessage, actualMessage);
            assertTrue(false, String.format("Expected message: '%s' but got: '%s'", existingUserMessage, actualMessage));
        }
    }
}