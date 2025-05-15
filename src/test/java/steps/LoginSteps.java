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
        assertTrue(loginPage.isErrorMessageVisible(message));
    }

    @When("User inputs {string} and {string} credentials")
    public void inputCredentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
    public void submitLoginForm() {
        loginPage.clickLoginButton();
    }

    @And("User inputs new valid credentials in New User Signup form")
    public void inputsNewValidCredentialsInNewUserSignupForm() {
        loginPage.enterSignupName(faker.name().fullName());
        loginPage.enterSignupEmail(faker.internet().emailAddress());
    }

    @And("User clicks on Signup button")
    public void clicksSignupButton() {
        loginPage.clickSignupButton();
    }

    @When("User inputs existing email {string} in New User Signup form")
    public void inputsExistingCredentialsInNewUserSignupForm(String email) {
        loginPage.enterSignupName(faker.name().fullName());
        loginPage.enterSignupEmail(email);
    }


/**
    @And("User clicks Delete account button")
    public void userClicksDeleteAccountButton() {
        loginPage.clickDeleteAccountButton();
    }
    **/
}


