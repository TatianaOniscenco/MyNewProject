package steps;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import context.ScenarioContext;
import context.ScenarioContextManager;
import context.UserContext;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {

    private final Logger log = LoggerFactory.getLogger(HomeSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final HomePage homePage = new HomePage(page);
    private final ScenarioContext context = ScenarioContextManager.get();

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        homePage.openHomePage();
        log.info("[ACTION] Opening homepage");
    }

    @When("User navigates to login page")
    public void navigateToLoginPage() {
        homePage.goToLoginPage();
        log.info("[ACTION] Navigating to login page");
    }

    @When("User clicks Delete Account button")
    public void clicksDeleteAccountButton() {
        homePage.clickDeleteButton();
        log.info("[ACTION] Clicking 'Delete Account' button");
    }

    @When("User clicks Logout button")
    public void userClicksLogoutButton() {
        homePage.clickLogoutButton();
        log.info("[ACTION] Clicking 'Logout' button");
    }

    @Then("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        String expectedUrl = ConfigReader.getInstance().get("base.url").replaceAll("/+$", "");
        String actualUrl = homePage.getCurrentUrl().replaceAll("/+$", "");

        if (expectedUrl.equals(actualUrl)) {
            log.info("[ASSERT] Homepage URL matched: {}", actualUrl);
        } else {
            log.error("[ASSERT][FAIL] Expected homepage URL: '{}', but got: '{}'", expectedUrl, actualUrl);
            assertEquals(expectedUrl, actualUrl, "Expected to be at: " + expectedUrl + ", but was: " + actualUrl);
        }
    }

    @Then("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = homePage.getLoggedInText();
        if (actualText.contains(expectedText)) {
            log.info("[ASSERT] Header contains expected text: '{}'", expectedText);
        } else {
            log.error("[ASSERT][FAIL] Header does not contain expected text. Expected: '{}', Found: '{}'", expectedText, actualText);
            assertTrue(actualText.contains(expectedText),
                    "Expected to find text: " + expectedText + " but found: " + actualText);
        }
    }

    @Then("System displays username up in the header")
    public void systemDisplaysUserNameFromContext() {
        UserContext user = context.get("user");
        String expectedText = user.getFullName();
        String actualText = homePage.getLoggedInText();

        if (actualText.contains(expectedText)) {
            log.info("[ASSERT] Header contains new user's name: '{}'", expectedText);
        } else {
            log.error("[ASSERT][FAIL] Header does not contain new user's name. Expected: '{}', Found: '{}'", expectedText, actualText);
            assertTrue(actualText.contains(expectedText),
                    "Expected to find: " + expectedText + ", but got: " + actualText);
        }
    }

    @Then("System displays username {string} up in the header")
    public void systemDisplaysAndUsernameUpInTheHeader(String fullName) {
        boolean isVisible = homePage.getLoggedInHeaderLocator(fullName).isVisible();

        if (isVisible) {
            log.info("[ASSERT] Header contains expected username: '{}'", fullName);
        } else {
            log.error("[ASSERT][FAIL] Header does NOT contain expected username: '{}'", fullName);
            assertTrue(false, "Expected username '" + fullName + "' not visible in header.");
        }
    }

}

