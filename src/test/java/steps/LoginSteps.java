package steps;

import api.actions.ApiActions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import config.ConfigReader;
import context.ScenarioContext;
import context.ScenarioContextManager;
import context.UserContext;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    private final Page page = PlaywrightFactory.getPage();
    private final LoginPage loginPage = new LoginPage(page);
    private final ScenarioContext context = ScenarioContextManager.get();
    private final Faker faker = new Faker();

    @Then("System displays the {string} message")
    public void systemDisplaysTheMessage(String expectedMessage) {
        try {
            String actualMessage = loginPage.getErrorMessage(expectedMessage);
            if (!expectedMessage.equals(actualMessage)) {
                log.error("[ASSERT][FAIL] Message mismatch — Expected: '{}', Actual: '{}'", expectedMessage, actualMessage);
            } else {
                log.info("[ASSERT] Message matched: '{}'", actualMessage);
            }
            assertEquals(expectedMessage, actualMessage);
        } catch (TimeoutError e) {
            log.error("[ASSERT][ERROR] Timeout while waiting for message: '{}'", expectedMessage, e);
            throw e;
        }
    }

    @When("User inputs {string} and {string} credentials")
    public void inputCredentials(String emailKey, String passwordKey) {
        String email = ConfigReader.get(emailKey.replace(" ", "."));
        String password = ConfigReader.get(passwordKey.replace(" ", "."));
        log.info("[ACTION] Inputting login credentials: email = {}, password = {}", email, password);
        loginPage.enterLoginEmail(email);
        loginPage.enterLoginPassword(password);
    }

    @When("User submits the login form")
    public void submitLoginForm() {
        log.info("[ACTION] Clicking login button");
        loginPage.clickLoginButton();
    }

    @And("User inputs new valid credentials in New User Signup form")
    public void inputsNewValidCredentialsInNewUserSignupForm() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        UserContext user = new UserContext();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        context.set("user", user);

        log.info("[DATA] Generated new user: {} {}", firstName, lastName);
        loginPage.enterSignupName(firstName + " " + lastName);
        loginPage.enterSignupEmail(email);
    }

    @And("User clicks on Signup button")
    public void clicksSignupButton() {
        log.info("[ACTION] Clicking signup button");
        loginPage.clickSignupButton();
    }

    @When("User inputs existing email {string} in New User Signup form")
    public void inputsExistingCredentialsInNewUserSignupForm(String email) {
        String name = faker.name().fullName();
        log.info("[DATA] Inputting existing email: {} with random name: {}", email, name);
        loginPage.enterSignupName(name);
        loginPage.enterSignupEmail(email);
    }

    @And("User inputs recent valid credentials to login")
    public void userInputsRecentValidCredentialsToLogin() {
        UserContext user = context.get("user");
        if (user == null) {
            log.error("[ERROR] UserContext is null when trying to input recent credentials");
            throw new IllegalStateException("No user context found");
        }

        log.info("[ACTION] Logging in with recent user: {}", user.getEmail());
        loginPage.enterLoginEmail(user.getEmail());
        loginPage.enterLoginPassword(user.getPassword());
    }
}



