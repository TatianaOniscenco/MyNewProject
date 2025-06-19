package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {
    Page page = Hooks.getPage();
    CartPage cartPage = new CartPage(page);

    @Then("selected {string} is visible in the cart")
    public void areProductsVisible(String productName) {
        Hooks.logToFile("[ASSERT] Verifying product is in cart: " + productName);
        assertTrue(cartPage.isProductInCart(productName), productName + " not found in cart");
    }

    @And("cart displays correct price {string} and quantity")
    public void verifyProductPriceAndQuantity(String expectedPrice) {
        Hooks.logToFile("[ASSERT] Verifying product price and quantity: Expected price = " + expectedPrice);
        assertTrue(cartPage.isPriceAndQuantityCorrect(expectedPrice), "Price or quantity incorrect");
    }

    @And("cart displays calculated total per product correctly")
    public void verifyProductTotalCalculation() {
        Hooks.logToFile("[ASSERT] Verifying total price per product is calculated correctly");
        assertTrue(cartPage.isTotalCorrect(), "Product total calculation is incorrect");
    }
}
