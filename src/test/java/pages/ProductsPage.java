package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductsPage {

    private final Page page;

    // Locators
    private static final String PRODUCT_NAME_LOCATOR = ".productinfo p";
    private static final String ADD_TO_CART_BUTTON = "xpath=//a[contains(@class,'add-to-cart')]";
    private static final String CONTINUE_SHOPPING_BUTTON = "button:has-text('Continue Shopping')";
    private static final String VIEW_CART_MODAL_BUTTON = "#cartModal a[href='/view_cart']";

    // Constructor
    public ProductsPage(Page page) {
        this.page = page;
    }

    // Actions
    public void hoverOverProductByName(String productName) {
        page.locator(PRODUCT_NAME_LOCATOR, new Page.LocatorOptions().setHasText(productName))
                .nth(0).hover();
    }

    public void clickAddToCartByName(String productName) {
        Locator productCard = page.locator(PRODUCT_NAME_LOCATOR,
                new Page.LocatorOptions().setHasText(productName)).nth(0).locator("..");

        productCard.locator(ADD_TO_CART_BUTTON).click();
    }

    public void clickContinueShopping() {
        page.locator(CONTINUE_SHOPPING_BUTTON).click();
    }

    public void clickViewCartFromModal() {
        page.locator(VIEW_CART_MODAL_BUTTON).click();
    }
}