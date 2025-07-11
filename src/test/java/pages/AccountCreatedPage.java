package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class AccountCreatedPage {
    private final Page page;

    // Constants (selectors and url fragment)
    private static final String CONTINUE_BUTTON = "[data-qa='continue-button']";
    private static final String ACCOUNT_CREATED_HEADING = "h2:has-text('Account Created!')";
    private static final String ACCOUNT_CREATED_URL_FRAGMENT = "/account_created";
    private static final String ACCOUNT_CREATED_MESSAGE = "[data-qa='account-created']";

    // Constructor
    public AccountCreatedPage(Page page) {
        this.page = page;
    }

    // Actions
    public void clickContinueButton() {
        page.locator(CONTINUE_BUTTON).click();
    }

    public boolean isAccountCreatedHeadingVisible() {
        Locator heading = page.locator(ACCOUNT_CREATED_HEADING);
        heading.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        return heading.isVisible();
    }

    public boolean isAtAccountCreatedUrl() {
        return page.url().contains(ACCOUNT_CREATED_URL_FRAGMENT);
    }

    public String getAccountCreatedMessage() {
        return page.locator(ACCOUNT_CREATED_MESSAGE).textContent().trim();
    }
}
