package steps;

import com.microsoft.playwright.Page;
import context.UserContext;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);
    Faker faker = new Faker();

    @Then("System displays the {string} message")
    public void systemDisplaysTheMessage(String message) {
        log.info("[ASSERT] Verifying error/success message: \"{}\" is visible", message);
        assertTrue(loginPage.isErrorMessageVisible(message));
    }

    @When("User inputs {string} and {string} credentials")
    public void inputCredentials(String login, String password) {
        log.info("[ACTION] Inputting login credentials: email = {}, password = {}", login, password);
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
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

        Hooks.setUserContext(user);  // ✅ store it in ThreadLocal

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
        UserContext user = Hooks.getUserContext();
        log.info("[ACTION] Logging in with recent user: {}", user.getEmail());
        loginPage.enterLoginEmail(user.getEmail());
        loginPage.enterLoginPassword(user.getPassword());
    }
}


