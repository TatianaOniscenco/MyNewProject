package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import pages.CartPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {
    Page page = Hooks.getPage();
    CartPage cartPage = new CartPage(page);

    @Then("selected {string} is visible in the cart")
    public void areProductsVisible(String productName) {
        assertTrue(cartPage.isProductInCart(productName), productName + " not found in cart");
    }

    @And("cart displays correct price {string} and quantity")
    public void verifyProductPriceAndQuantity(String expectedPrice) {
        assertTrue(cartPage.isPriceAndQuantityCorrect(expectedPrice), "Price or quantity incorrect");
    }

    @And("cart displays calculated total per product correctly")
    public void verifyProductTotalCalculation() {
        assertTrue(cartPage.isTotalCorrect(), "Product total calculation is incorrect");
    }

}
