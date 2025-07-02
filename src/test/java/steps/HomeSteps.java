package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static hooks.Hooks.getUserContext;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {
    private static final Logger log = LoggerFactory.getLogger(HomeSteps.class);

    private final Page page = Hooks.getPage();

    private HomePage getHomePage() {
        return new HomePage(page);
    }


    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        log.info("[ACTION] Opening homepage");
        getHomePage().openHomePage();
    }

    @And("User navigates to login page")
    public void navigateToLoginPage() {
        log.info("[ACTION] Navigating to login page");
        getHomePage().goToLoginPage();
    }

    @And("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        getHomePage().assertRedirectedToHomeUrl();
    }

    @And("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = getHomePage().getLoggedInText();
        log.info("[ASSERT] Checking header contains: \"{}\" | Found: \"{}\"", expectedText, actualText);
        assertTrue(actualText.contains(expectedText),
                "Expected to find text: " + expectedText + " but found: " + actualText);
    }

    @When("User clicks Delete Account button")
    public void clicksDeleteAccountButton() {
        log.info("[ACTION] Clicking 'Delete Account' button");
        getHomePage().clickDeleteButton();
    }

    @And("System displays username up in the header")
    public void systemDisplaysAndUsernameUpInTheHeader() {
        String fullName = getUserContext().getFullName();
        log.info("[ASSERT] Checking header visibility for: {}", fullName);
        assertThat(getHomePage().getLoggedInHeaderLocator()).isVisible();
    }

    @When("User clicks Logout button")
    public void userClicksLogoutButton() {
        log.info("[ACTION] Clicking 'Logout' button");
        getHomePage().clickLogoutButton();
    }
}
