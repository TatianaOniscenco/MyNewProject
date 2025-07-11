package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;

    // Constants
    private static final String CART_DESCRIPTION_LOCATOR = ".cart_description";
    private static final String CART_PRICE_LOCATOR = ".cart_price";
    private static final String PRODUCT_PRICE_LOCATOR = "td.cart_price > p";
    private static final String CART_QUANTITY_LOCATOR = ".cart_quantity button";
    private static final String CART_TOTAL_LOCATOR = ".cart_total";
    private static final String CART_ROWS_LOCATOR = "tr[id^='product-']";
    private static final String CART_PAGE_URL_FRAGMENT = "/view_cart";
    private static final String BREADCRUMB_CART = "ol.breadcrumb li.active:has-text(\"Shopping Cart\")";


    // Constructor
    public CartPage(Page page) {
        this.page = page;
    }

    // Actions
    public boolean isProductInCart(String productName) {
        return page.locator(CART_DESCRIPTION_LOCATOR)
                .filter(new Locator.FilterOptions().setHasText(productName))
                .isVisible();
    }
    /**
     * Returns the product price displayed in the cart as a string (e.g., "Rs. 400").
     */
    public String getDisplayedPrice() {
        return page.locator(PRODUCT_PRICE_LOCATOR).textContent().trim();
    }

    public boolean isCartPageVisible() {
        try {
            return page.locator(BREADCRUMB_CART).isVisible()
                    && page.url().contains(CART_PAGE_URL_FRAGMENT);
        } catch (Exception e) {
            return false;
        }

    }
}

