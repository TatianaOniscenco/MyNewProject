package steps;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {
    private static final Logger log = LoggerFactory.getLogger(HomeSteps.class);

    HomePage homePage = new HomePage(Hooks.getPage());

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        log.info("[ACTION] Opening homepage");
        homePage.openHomePage();
    }

    @And("User navigates to login page")
    public void navigateToLoginPage() {
        log.info("[ACTION] Navigating to login page");
        homePage.goToLoginPage();
    }

    @And("User is redirected to homepage")
    public void isRedirectedToHomepage() {
        log.info("[ASSERT] Checking homepage is visible");
        homePage.isVisible();
    }

    @And("System displays {string} up in the header")
    public void systemDisplaysUserName(String expectedText) {
        String actualText = homePage.getLoggedInText();
        log.info("[ASSERT] Checking header contains: \"{}\" | Found: \"{}\"", expectedText, actualText);
        assertTrue(actualText.contains(expectedText),
                "Expected to find text: " + expectedText + " but found: " + actualText);
    }

    @When("User clicks Delete Account button")
    public void clicksDeleteAccountButton() {
        log.info("[ACTION] Clicking 'Delete Account' button");
        homePage.clickDeleteButton();
    }

    @And("System displays username up in the header")
    public void systemDisplaysAndUsernameUpInTheHeader() {
        log.info("[ASSERT] Checking logged-in username is visible in header");
        assertThat(homePage.getLoggedInHeaderLocator()).isVisible();
    }

    @When("User clicks Logout button")
    public void userClicksLogoutButton() {
        log.info("[ACTION] Clicking 'Logout' button");
        homePage.clickLogoutButton();
    }
}
