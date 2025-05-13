package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.HomePage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {
    HomePage homePage = new HomePage(Hooks.getPage());
    LoginPage loginPage = new LoginPage(Hooks.getPage());

    @Given("User is on the login page")
    public void isOnTheLoginPage() {
        homePage.openHomePage();
        homePage.goToLoginPage();
    }

    @Then("System displays the {string} message")
    public void systemDisplaysTheMessage(String message) {
        assertTrue(loginPage.isErrorMessageVisible(message));
    }
}
