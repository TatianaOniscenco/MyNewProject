package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.AccountCreatedPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedSteps {
    private static final Logger log = LoggerFactory.getLogger(AccountCreatedSteps.class);

    private final Page page = Hooks.getPage();
    private final AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);

    @Then("User is redirected to Account Created page")
    public void isOnAccountCreatedPage() {
        log.info("[ASSERT] Verifying user is on Account Created page");

        boolean isVisible = accountCreatedPage.isAccountCreatedHeadingVisible();
        boolean isCorrectUrl = accountCreatedPage.isAtAccountCreatedUrl();

        assertTrue(isVisible, "'Account Created!' heading is not visible.");
        assertTrue(isCorrectUrl, "Unexpected Account Created URL: " + page.url());
    }

    @When("User clicks Continue button")
    public void clicksContinueButton() {
        log.info("[ACTION] Clicking Continue button on Account Created page");
        accountCreatedPage.clickContinueButton();
    }
}
