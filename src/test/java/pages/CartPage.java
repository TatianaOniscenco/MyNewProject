package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;

    // === Constants ===
    private static final String CART_DESCRIPTION_LOCATOR = ".cart_description";
    private static final String CART_PRICE_LOCATOR = ".cart_price";
    private static final String CART_QUANTITY_LOCATOR = ".cart_quantity button";
    private static final String CART_TOTAL_LOCATOR = ".cart_total";
    private static final String CART_ROWS_LOCATOR = "tr[id^='product-']";
    private static final String CART_PAGE_URL_FRAGMENT = "/view_cart";
    private static final String BREADCRUMB_CART = "ol.breadcrumb li.active:has-text(\"Shopping Cart\")";


    // === Constructor ===
    public CartPage(Page page) {
        this.page = page;
    }

    // === Actions & Assertions ===
    public boolean isProductInCart(String productName) {
        return page.locator(CART_DESCRIPTION_LOCATOR)
                .filter(new Locator.FilterOptions().setHasText(productName))
                .isVisible();
    }

    public boolean isPriceAndQuantityCorrect(String expectedPrice) {
        Locator cartRows = page.locator(CART_ROWS_LOCATOR);

        for (int i = 0; i < cartRows.count(); i++) {
            Locator row = cartRows.nth(i);
            String price = row.locator(CART_PRICE_LOCATOR).innerText().replaceAll("[^0-9]", "");
            String quantity = row.locator(CART_QUANTITY_LOCATOR).innerText().trim();

            if (price.equals(expectedPrice) && Integer.parseInt(quantity) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isTotalCorrect() {
        Locator cartRows = page.locator(CART_ROWS_LOCATOR);

        for (int i = 0; i < cartRows.count(); i++) {
            Locator row = cartRows.nth(i);

            int price = Integer.parseInt(row.locator(CART_PRICE_LOCATOR).innerText().replaceAll("[^0-9]", ""));
            int quantity = Integer.parseInt(row.locator(CART_QUANTITY_LOCATOR).innerText().trim());
            int total = Integer.parseInt(row.locator(CART_TOTAL_LOCATOR).innerText().replaceAll("[^0-9]", ""));

            if (price * quantity != total) {
                return false;
            }
        }
        return true;
    }

    public boolean isCartPageVisible() {
        try {
            return page.locator(BREADCRUMB_CART).isVisible()
                    && page.url().contains("/view_cart");
        } catch (Exception e) {
            return false;
        }
    }
}

