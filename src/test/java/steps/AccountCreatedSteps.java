package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import pages.AccountCreatedPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedSteps {
    Page page = Hooks.getPage();
    AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);

    @And ("User is redirected to Account Created page")
        public void isOnAccountCreatedPage() {
        accountCreatedPage.isVisible();
        }

    @And("User clicks Continue button")
    public void clicksContinueButton() {
        accountCreatedPage.clickContinueButton();
    }
}
