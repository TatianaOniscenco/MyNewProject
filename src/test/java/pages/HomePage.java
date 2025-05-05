package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public class HomePage {

    private final Page page;

    // Selectors
    private final String signupLoginLink = "[href='/login']";
    private final String productsLink = "[href='/products']";

    public HomePage(Page page) {
        this.page = page;
    }

    public void openHomePage() {
        page.navigate("https://www.automationexercise.com",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForSelector("[href='/login']");
    }

    public void goToLoginPage() {
        page.locator(signupLoginLink).click();
    }

    public void goToProductsPage() {
        page.locator(productsLink).click();
    }
}
