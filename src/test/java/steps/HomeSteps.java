package steps;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {
    HomePage homePage = new HomePage(Hooks.getPage());

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        homePage.openHomePage();
    }

    @And("User navigates to login page")
    public void navigateToLoginPage() {
        homePage.goToLoginPage();
    }

    @And("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        homePage.isVisible();
    }
    @And("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = homePage.getLoggedInText();
        assertTrue(actualText.contains(expectedText),
                "Expected to find text: " + expectedText + " but found: " + actualText);
    }
}
