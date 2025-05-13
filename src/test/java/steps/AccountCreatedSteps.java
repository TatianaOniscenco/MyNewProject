package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import pages.AccountCreatedPage;

public class AccountCreatedSteps {
    Page page = Hooks.getPage();
    AccountCreatedPage accountCreatedPage = new AccountCreatedPage(page);
}
