package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductsPage {
    private final Page page;

    public ProductsPage(Page page) {
        this.page = page;
    }

    public void hoverOverProductByName(String productName) {
        page.locator(".productinfo p", new Page.LocatorOptions().setHasText(productName)).nth(0).hover();
    }

    public void clickAddToCartByName(String productName) {
        Locator productCard = page.locator(".productinfo p", new Page.LocatorOptions().setHasText(productName)).nth(0).locator("..");
        productCard.locator("xpath=//a[contains(@class,'add-to-cart')]").click();
    }

    public void clickContinueShopping() {
        page.locator("button:has-text('Continue Shopping')").click();
    }

    public void clickViewCartFromModal() {
        page.locator("#cartModal a[href='/view_cart']").click();
    }


}