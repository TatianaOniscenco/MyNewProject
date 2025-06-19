package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);
    Faker faker = new Faker();

    @Then("System displays the {string} message")
    public void systemDisplaysTheMessage(String message) {
        Hooks.logToFile("[ASSERT] Verifying error/success message: \"" + message + "\" is visible");
        assertTrue(loginPage.isErrorMessageVisible(message));
    }

    @When("User inputs {string} and {string} credentials")
    public void inputCredentials(String login, String password) {
        Hooks.logToFile("[ACTION] Inputting login credentials: email = " + login + ", password = " + password);
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
    public void submitLoginForm() {
        Hooks.logToFile("[ACTION] Clicking login button");
        loginPage.clickLoginButton();
    }

    @And("User inputs new valid credentials in New User Signup form")
    public void inputsNewValidCredentialsInNewUserSignupForm() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        String email = faker.internet().emailAddress();

        Hooks.setFirstName(firstName);
        Hooks.setLastName(lastName);
        Hooks.setEmail(email);

        Hooks.logToFile("[DATA] Generated user for signup: " + fullName + " <" + email + ">");
        loginPage.enterSignupName(fullName);
        loginPage.enterSignupEmail(email);
    }

    @And("User clicks on Signup button")
    public void clicksSignupButton() {
        Hooks.logToFile("[ACTION] Clicking signup button");
        loginPage.clickSignupButton();
    }

    @When("User inputs existing email {string} in New User Signup form")
    public void inputsExistingCredentialsInNewUserSignupForm(String email) {
        String name = faker.name().fullName();
        Hooks.logToFile("[DATA] Inputting existing email: " + email + " with random name: " + name);
        loginPage.enterSignupName(name);
        loginPage.enterSignupEmail(email);
    }

    @And("User inputs recent valid credentials to login")
    public void userInputsRecentValidCredentialsToLogin() {
        Hooks.logToFile("[ACTION] Logging in with recent user: " + Hooks.getEmail());
        loginPage.enterLoginEmail(Hooks.getEmail());
        loginPage.enterLoginPassword(Hooks.getPassword());
    }
}


