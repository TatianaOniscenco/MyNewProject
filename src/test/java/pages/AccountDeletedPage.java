package pages;

import com.microsoft.playwright.Page;

public class AccountDeletedPage {

    private final Page page;

    // Constants (selectors and url fragment)
    private static final String ACCOUNT_DELETED_MESSAGE = "[data-qa='account-deleted']";

    // Constructor
    public AccountDeletedPage(Page page) {
        this.page = page;
    }

    // Actions
    public String getAccountDeletedMessage() {
        return page.locator(ACCOUNT_DELETED_MESSAGE).textContent().trim();
    }
}
