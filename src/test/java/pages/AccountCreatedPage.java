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

    // Constructor
    public AccountCreatedPage(Page page) {
        this.page = page;
    }

    // Actions
    public void clickContinueButton() {
        page.locator(CONTINUE_BUTTON)
                .click(new Locator.ClickOptions().setTimeout(1000));
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public boolean isAccountCreatedHeadingVisible() {
        Locator heading = page.locator(ACCOUNT_CREATED_HEADING);
        heading.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        return heading.isVisible();
    }

    public boolean isAtAccountCreatedUrl() {
        return page.url().contains(ACCOUNT_CREATED_URL_FRAGMENT);
    }
}
