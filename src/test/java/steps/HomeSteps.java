package steps;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {
    HomePage homePage = new HomePage(Hooks.getPage());

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        Hooks.logToFile("[ACTION] Opening homepage");
        homePage.openHomePage();
    }

    @And("User navigates to login page")
    public void navigateToLoginPage() {
        Hooks.logToFile("[ACTION] Navigating to login page");
        homePage.goToLoginPage();
    }

    @And("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        Hooks.logToFile("[ASSERT] Checking homepage is visible");
        homePage.isVisible();
    }

    @And("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = homePage.getLoggedInText();
        Hooks.logToFile("[ASSERT] Checking header contains: \"" + expectedText + "\" | Found: \"" + actualText + "\"");
        assertTrue(actualText.contains(expectedText),
                "Expected to find text: " + expectedText + " but found: " + actualText);
    }

    @When("User clicks Delete Account button")
    public void clicksDeleteAccountButton() {
        Hooks.logToFile("[ACTION] Clicking 'Delete Account' button");
        homePage.clickDeleteButton();
    }

    @And("System displays username up in the header")
    public void systemDisplaysAndUsernameUpInTheHeader() {
        Hooks.logToFile("[ASSERT] Checking logged-in username is visible in header");
        assertThat(homePage.getLoggedInHeaderLocator()).isVisible();
    }

    @When("User clicks Logout button")
    public void userClicksLogoutButton() {
        Hooks.logToFile("[ACTION] Clicking 'Logout' button");
        homePage.clickLogoutButton();
    }
}