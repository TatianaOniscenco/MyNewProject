package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);

    @When("User enters invalid {string} and {string} credentials")
    public void user_enters_incorrect_credentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
    public void user_submits_the_login_form() {
        loginPage.clickLoginButton();
    }

    @When("User enters valid {string} and {string} credentials")
    public void userEntersValidAndCredentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }


    @And("System displays {string}")
    public void systemDisplays(String expectedText) {
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


