package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Private locators
    private final String signupLink = "text= Signup / Login";
    private final String signupNameField = "[data-qa='signup-name']";
    private final String signupEmailField = "[data-qa='signup-email']";
    private final String signupButton = "[data-qa='signup-button']";
    private final String loginEmailField = "[data-qa='login-email']";
    private final String loginPasswordField = "[data-qa='login-password']";
    private final String loginButton = "[data-qa='login-button']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToLoginPage() {
        page.navigate("https://www.automationexercise.com");
        page.waitForLoadState();
        page.locator(signupLink).click();
        page.waitForSelector(signupNameField);
        page.waitForSelector(loginEmailField);
    }

    // --- Login section ---
    public void enterLoginEmail(String email) {
        page.locator(loginEmailField).fill(email);
    }

    public void enterLoginPassword(String password) {
        page.locator(loginPasswordField).fill(password);
    }

    public void clickLoginButton() {
        page.locator(loginButton).click();
    }

    // --- Registration section ---
    public void enterSignupName(String name) {
        page.locator(signupNameField).fill(name);
    }

    public void enterSignupEmail(String email) {
        page.locator(signupEmailField).fill(email);
    }

    public void clickSignupButton() {
        page.locator(signupButton).click();
    }

    // --- Assertion helper ---
    public boolean isErrorMessageVisible(String message) {
        return page.locator("text=" + message).isVisible();
    }
}
