package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.Then;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {
    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);

    @Then("System displays the {string} message")
    public void systemDisplaysTheMessage(String message) {
        assertTrue(loginPage.isErrorMessageVisible(message));
    }
}
