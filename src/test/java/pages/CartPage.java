package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    public boolean isProductInCart(String productName) {
        return page.locator(".cart_description")
                .filter(new Locator.FilterOptions().setHasText(productName))
                .isVisible();
    }

    public boolean isPriceAndQuantityCorrect(String expectedPrice) {
        Locator cartRows = page.locator("tr[id^='product-']");

        for (int i = 0; i < cartRows.count(); i++) {
            Locator row = cartRows.nth(i);

            String price = row.locator(".cart_price").innerText().replaceAll("[^0-9]", "");
            String quantity = row.locator(".cart_quantity button").innerText().trim();

            if (price.equals(expectedPrice) && Integer.parseInt(quantity) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isTotalCorrect() {
        Locator cartRows = page.locator("tr[id^='product-']");

        for (int i = 0; i < cartRows.count(); i++) {
            Locator row = cartRows.nth(i);

            int price = Integer.parseInt(row.locator(".cart_price").innerText().replaceAll("[^0-9]", ""));
            int quantity = Integer.parseInt(row.locator(".cart_quantity button").innerText().trim());
            int total = Integer.parseInt(row.locator(".cart_total").innerText().replaceAll("[^0-9]", ""));

            if (price * quantity != total) {
                return false;
            }
        }
        return true;
    }
}

