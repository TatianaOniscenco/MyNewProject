package steps;

import com.microsoft.playwright.Page;
import context.ScenarioContext;
import context.ScenarioContextManager;
import factory.PlaywrightFactory;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private static final Logger log = LoggerFactory.getLogger(CartSteps.class);

    private final Page page = PlaywrightFactory.getPage();
    private final CartPage cartPage = new CartPage(page);
    private final ScenarioContext context = ScenarioContextManager.get();

    @Then("Selected {string} is visible in the cart")
    public void areProductsVisible(String productName) {
        boolean visible = cartPage.isProductInCart(productName);
        if (!visible) {
            log.error("[ASSERT][FAIL] Product '{}' not found in cart.", productName);
        } else {
            log.info("[ASSERT] Product '{}' is visible in cart.", productName);
        }
        assertTrue(visible, productName + " not found in cart");
    }

    @Then("Cart displays correct price {string} and quantity")
    public void verifyProductPriceAndQuantity(String expectedPrice) {
        boolean correct = cartPage.isPriceAndQuantityCorrect(expectedPrice);
        if (!correct) {
            log.error("[ASSERT][FAIL] Cart displays incorrect price or quantity. Expected price: {}", expectedPrice);
        } else {
            log.info("[ASSERT] Cart displays correct price and quantity for price: {}", expectedPrice);
        }
        assertTrue(correct, "Price or quantity incorrect");
    }

    @Then("Cart displays calculated total per product correctly")
    public void verifyProductTotalCalculation() {
        boolean correctTotal = cartPage.isTotalCorrect();
        if (!correctTotal) {
            log.error("[ASSERT][FAIL] Total price per product calculation is incorrect.");
        } else {
            log.info("[ASSERT] Total price per product is calculated correctly.");
        }
        assertTrue(correctTotal, "Product total calculation is incorrect");
    }

    @Then("User is redirected to Cart Page")
    public void userIsRedirectedToCartPage() {
        boolean onCartPage = cartPage.isCartPageVisible();
        if (!onCartPage) {
            log.error("[ASSERT][FAIL] User is not on the Cart Page. Current URL: {}", page.url());
        } else {
            log.info("[ASSERT] User is successfully redirected to the Cart Page.");
        }
        assertTrue(onCartPage, "User is not on the Cart Page");
    }
}