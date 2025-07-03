package steps;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ProductsPage;

public class ProductsSteps {
    private static final Logger log = LoggerFactory.getLogger(ProductsSteps.class);

    Page page = Hooks.getPage();
    ProductsPage productsPage = new ProductsPage(page);

    @Given("User is on Products page")
    public void isOnProductsPage() {
        log.info("[ACTION] Navigating to Products page");
        String url = ConfigReader.get("base.url") + "/products";
        page.navigate(url);
        page.waitForLoadState();
    }

    @When("adds {string} product to the cart")
    public void addProductToCart(String productName) {
        log.info("[ACTION] Adding product to cart: {}", productName);
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
    }

    @And("chooses to continue shopping")
    public void chooseToContinueShopping() {
        log.info("[ACTION] Clicking Continue Shopping");
        productsPage.clickContinueShopping();
    }

    @And("user views the cart clicking on the button in the modal")
    public void viewTheCart() {
        log.info("[ACTION] Viewing the cart from modal");
        productsPage.clickViewCartFromModal();
    }
}
