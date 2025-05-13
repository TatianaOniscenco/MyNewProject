package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import pages.LoginPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);

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


/**
    @And("User clicks Delete account button")
    public void userClicksDeleteAccountButton() {
        loginPage.clickDeleteAccountButton();
    }
    **/
}


