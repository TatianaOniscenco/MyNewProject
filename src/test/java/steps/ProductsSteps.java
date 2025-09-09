package steps;

import com.microsoft.playwright.Page;
import context.ScenarioContext;
import context.ScenarioContextManager;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ProductsPage;

public class ProductsSteps {

    private final Logger log = LoggerFactory.getLogger(ProductsSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final ProductsPage productsPage = new ProductsPage(page);
    private final ScenarioContext context = ScenarioContextManager.get();

    @Given("User is on Products page")
    public void isOnProductsPage() {
        productsPage.navigateToProductsPage();
        log.info("[INFO] User is on Products page");
    }

    @When("Adds {string} product to the cart")
    public void addProductToCart(String productName) {
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
        context.set("selectedProduct", productName);
        log.info("[ACTION] Adding product to cart: {}", productName);
    }

    @When("Chooses to continue shopping")
    public void chooseToContinueShopping() {
        productsPage.clickContinueShopping();
        log.info("[ACTION] Clicking Continue Shopping");
    }

    @When("User views the cart clicking on the button in the modal")
    public void viewTheCart() {
        productsPage.clickViewCartFromModal();
        log.info("[ACTION] Viewing the cart from modal");
    }
}
