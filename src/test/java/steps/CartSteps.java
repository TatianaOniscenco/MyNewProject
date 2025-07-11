package steps;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private final Logger log = LoggerFactory.getLogger(CartSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final CartPage cartPage = new CartPage(page);

    @Then("Selected {string} is visible in the cart")
    public void areProductsVisible(String productName) {
        boolean visible = cartPage.isProductInCart(productName);
        if (visible) {
            log.info("[ASSERT] Product '{}' is visible in cart.", productName);
        } else {
            log.error("[ASSERT][FAIL] Product '{}' not found in cart.", productName);
            assertTrue(false, productName + " not found in cart");
        }
    }

    @Then("Cart displays correct price {string}")
    public void verifyProductPrice(String expectedPrice) {
        String actualPrice = cartPage.getDisplayedPrice();
        if (expectedPrice.equals(actualPrice)) {
            log.info("[ASSERT] Cart displays correct price. Price: '{}'", actualPrice);
        } else {
            log.error("[ASSERT][FAIL] Cart shows incorrect details — Expected price: '{}', but was price: '{}'",
                    expectedPrice, actualPrice);
            assertEquals(expectedPrice, actualPrice,
                    String.format("Price incorrect — Expected: '%s', but was: '%s'", expectedPrice, actualPrice));
        }
    }

    @Then("User is redirected to Cart Page")
    public void userIsRedirectedToCartPage() {
        boolean onCartPage = cartPage.isCartPageVisible();
        if (onCartPage) {
            log.info("[ASSERT] User is successfully redirected to the Cart Page.");
        } else {
            log.error("[ASSERT][FAIL] User is not on the Cart Page. Current URL: {}", page.url());
            assertTrue(false, "User is not on the Cart Page");
        }
    }
}