package pages;

import com.microsoft.playwright.Page;

public class AccountCreatedPage {
    private final Page page;

    private final String loggedInTextLocator = "li:has-text('Logged in as')";

    public AccountCreatedPage(Page page) {
        this.page = page;
    }

    public String getLoggedInText() {
        return page.locator(loggedInTextLocator).innerText().trim();
    }


}
