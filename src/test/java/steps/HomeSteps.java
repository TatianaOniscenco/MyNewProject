package steps;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {
    private static final Logger log = LoggerFactory.getLogger(HomeSteps.class);
    private final Page page = Hooks.getPage();
    private final HomePage homePage = new HomePage(page);

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        log.info("[ACTION] Opening homepage");
        homePage.openHomePage();
    }

    @When("User navigates to login page")
    public void navigateToLoginPage() {
        log.info("[ACTION] Navigating to login page");
        homePage.goToLoginPage();
    }

    @When("User clicks Delete Account button")
    public void clicksDeleteAccountButton() {
        log.info("[ACTION] Clicking 'Delete Account' button");
        homePage.clickDeleteButton();
    }

    @When("User clicks Logout button")
    public void userClicksLogoutButton() {
        log.info("[ACTION] Clicking 'Logout' button");
        homePage.clickLogoutButton();
    }

    @Then("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        String expectedUrl = ConfigReader.get("base.url").replaceAll("/+$", "");
        String actualUrl = homePage.getCurrentUrl().replaceAll("/+$", "");

        log.info("[ASSERT] Validating homepage URL: expected='{}', actual='{}'", expectedUrl, actualUrl);
        assertEquals(actualUrl, expectedUrl, "Expected to be at: " + expectedUrl + ", but was: " + actualUrl);
    }

    @Then("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = homePage.getLoggedInText();
        log.info("[ASSERT] Checking header for existing user contains: \"{}\" | Found: \"{}\"", expectedText, actualText);
        assertTrue(actualText.contains(expectedText),
                "Expected to find text: " + expectedText + " but found: " + actualText);
    }
    @Then("System displays username up in the header")
    public void systemDisplaysUserNameFromContext() {
        String expectedText = Hooks.getUserContext().getFullName();
        String actualText = homePage.getLoggedInText();
        log.info("[ASSERT] Checking header for new user contains: \"{}\" | Found: \"{}\"", expectedText, actualText);
        assertTrue(actualText.contains(expectedText),
                "Expected to find: " + expectedText + ", but got: " + actualText);
    }

    @Then("System displays username {string} up in the header")
    public void systemDisplaysAndUsernameUpInTheHeader(String fullName) {
        log.info("[ASSERT] Checking header visibility for: {}", fullName);
        assertThat(homePage.getLoggedInHeaderLocator(fullName)).isVisible();
    }
}
