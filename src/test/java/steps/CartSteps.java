package steps;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.CartPage;
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

    @Then("Cart displays correct price {string} and quantity")
    public void verifyProductPriceAndQuantity(String expectedPrice) {
        boolean correct = cartPage.isPriceAndQuantityCorrect(expectedPrice);
        if (correct) {
            log.info("[ASSERT] Cart displays correct price and quantity for price: {}", expectedPrice);
        } else {
            log.error("[ASSERT][FAIL] Cart displays incorrect price or quantity. Expected price: {}", expectedPrice);
            assertTrue(false, "Price or quantity incorrect");
        }
    }

    @Then("Cart displays calculated total per product correctly")
    public void verifyProductTotalCalculation() {
        boolean correctTotal = cartPage.isTotalCorrect();
        if (correctTotal) {
            log.info("[ASSERT] Total price per product is calculated correctly.");
        } else {
            log.error("[ASSERT][FAIL] Total price per product calculation is incorrect.");
            assertTrue(false, "Product total calculation is incorrect");
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