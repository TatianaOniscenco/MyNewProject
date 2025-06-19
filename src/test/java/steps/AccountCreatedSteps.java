package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import pages.AccountCreatedPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedSteps {
    Page page = Hooks.getPage();
    AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);

    @And("User is redirected to Account Created page")
    public void isOnAccountCreatedPage() {
        Hooks.logToFile("[ASSERT] Verifying user is on Account Created page");
        accountCreatedPage.isVisible();
    }

    @And("User clicks Continue button")
    public void clicksContinueButton() {
        Hooks.logToFile("[ACTION] Clicking Continue button on Account Created page");
        accountCreatedPage.clickContinueButton();
    }
}
