package pages;

import com.microsoft.playwright.Page;

public class AccountCreatedPage {
    private final Page page;
    private final String continueButton = "[data-qa='continue-button']";


    public AccountCreatedPage(Page page) {
        this.page = page;
    }

    public void clickContinueButton() {
        page.locator(continueButton).click();
    }
}
