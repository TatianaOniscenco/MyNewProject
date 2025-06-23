package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.AccountCreatedPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedSteps {
    private static final Logger log = LoggerFactory.getLogger(AccountCreatedSteps.class);

    Page page = Hooks.getPage();
    AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);

    @And("User is redirected to Account Created page")
    public void isOnAccountCreatedPage() {
        log.info("[ASSERT] Verifying user is on Account Created page");
        accountCreatedPage.isVisible();
    }

    @And("User clicks Continue button")
    public void clicksContinueButton() {
        log.info("[ACTION] Clicking Continue button on Account Created page");
        accountCreatedPage.clickContinueButton();
    }
}
