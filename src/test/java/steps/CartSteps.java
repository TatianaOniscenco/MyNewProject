package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {
    private static final Logger log = LoggerFactory.getLogger(CartSteps.class);

    Page page = Hooks.getPage();
    CartPage cartPage = new CartPage(page);

    @Then("selected {string} is visible in the cart")
    public void areProductsVisible(String productName) {
        log.info("[ASSERT] Verifying product is in cart: {}", productName);
        assertTrue(cartPage.isProductInCart(productName), productName + " not found in cart");
    }

    @And("cart displays correct price {string} and quantity")
    public void verifyProductPriceAndQuantity(String expectedPrice) {
        log.info("[ASSERT] Verifying product price and quantity: Expected price = {}", expectedPrice);
        assertTrue(cartPage.isPriceAndQuantityCorrect(expectedPrice), "Price or quantity incorrect");
    }

    @And("cart displays calculated total per product correctly")
    public void verifyProductTotalCalculation() {
        log.info("[ASSERT] Verifying total price per product is calculated correctly");
        assertTrue(cartPage.isTotalCorrect(), "Product total calculation is incorrect");
    }

    @Then("User is redirected to Cart Page")
    public void userIsRedirectedToCartPage() {
        log.info("[ASSERT] Verifying user is on the Cart Page");
        assertTrue(cartPage.isCartPageVisible(), "User is not on the Cart Page");
    }
}
