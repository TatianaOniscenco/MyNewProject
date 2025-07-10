package steps;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.AccountCreatedPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedSteps {

    private final Logger log = LoggerFactory.getLogger(AccountCreatedSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);

    @Then("User is redirected to Account Created page")
    public void isOnAccountCreatedPage() {

        boolean headingVisible = accountCreatedPage.isAccountCreatedHeadingVisible();
        if (headingVisible) {
            log.info("[ASSERT] Heading 'Account Created!' is visible.");
        } else {
            log.error("[ASSERT][FAIL] 'Account Created!' heading is not visible.");
            assertTrue(false, "'Account Created!' heading is not visible.");
        }

        boolean correctUrl = accountCreatedPage.isAtAccountCreatedUrl();
        if (correctUrl) {
            log.info("[ASSERT] Correct Account Created URL confirmed: {}", page.url());
        } else {
            log.error("[ASSERT][FAIL] Unexpected Account Created URL: {}", page.url());
            assertTrue(false, "User is not at the expected Account Created URL.");
        }
    }

    @When("User clicks Continue button")
    public void clicksContinueButton() {
        accountCreatedPage.clickContinueButton();
        log.info("[ACTION] Clicking Continue button on Account Created page");
    }
}
