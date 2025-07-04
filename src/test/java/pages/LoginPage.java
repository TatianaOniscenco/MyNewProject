package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Constants
    private static final String SIGNUP_NAME_FIELD = "[data-qa='signup-name']";
    private static final String SIGNUP_EMAIL_FIELD = "[data-qa='signup-email']";
    private static final String SIGNUP_BUTTON = "[data-qa='signup-button']";
    private static final String LOGIN_EMAIL_FIELD = "[data-qa='login-email']";
    private static final String LOGIN_PASSWORD_FIELD = "[data-qa='login-password']";
    private static final String LOGIN_BUTTON = "[data-qa='login-button']";
    private static final String ERROR_MESSAGE_TEMPLATE = "text=%s";

    // Constructor
        public LoginPage(Page page) {
        this.page = page;
    }

    // Actions
    public void enterLoginEmail(String email) {
        page.locator(LOGIN_EMAIL_FIELD).fill(email);
    }

    public void enterLoginPassword(String password) {
        page.locator(LOGIN_PASSWORD_FIELD).fill(password);
    }

    public void clickLoginButton() {
        page.locator(LOGIN_BUTTON).click();
    }

    public void enterSignupName(String name) {
        page.locator(SIGNUP_NAME_FIELD).fill(name);
    }

    public void enterSignupEmail(String email) {
        page.locator(SIGNUP_EMAIL_FIELD).fill(email);
    }

    public void clickSignupButton() {
        page.locator(SIGNUP_BUTTON).click();
    }

    public String getErrorMessage(String message) {
        return page.locator(String.format(ERROR_MESSAGE_TEMPLATE, message))
                .textContent()
                .trim();
    }
}
