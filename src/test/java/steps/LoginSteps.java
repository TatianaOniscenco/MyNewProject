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

    @When("User enters invalid {string} and {string} credentials")
    public void inputInvalidCredentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
    public void submitLoginForm() {
        loginPage.clickLoginButton();
    }

    @When("User enters valid {string} and {string} credentials")
    public void inputValidCredentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("System displays {string}")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = page.locator("li:has-text('Logged in as Tatiana Oniscenco')").innerText().trim();
        assertTrue(actualText.contains(expectedText));
    }

/**
    @And("User clicks Delete account button")
    public void userClicksDeleteAccountButton() {
        loginPage.clickDeleteAccountButton();
    }
    **/
}


