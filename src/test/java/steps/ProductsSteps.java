package steps;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import context.ScenarioContext;
import context.ScenarioContextManager;
import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ProductsPage;

public class ProductsSteps {

    private static final Logger log = LoggerFactory.getLogger(ProductsSteps.class);

    private final Page page = PlaywrightFactory.getPage();
    private final ProductsPage productsPage = new ProductsPage(page);
    private final ScenarioContext context = ScenarioContextManager.get();

    @Given("User is on Products page")
    public void isOnProductsPage() {
        String url = ConfigReader.get("base.url") + "/products";
        log.info("[ACTION] Navigating to Products page: {}", url);
        page.navigate(url);
        page.waitForLoadState();
    }

    @When("Adds {string} product to the cart")
    public void addProductToCart(String productName) {
        log.info("[ACTION] Adding product to cart: {}", productName);
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
        context.set("selectedProduct", productName);
    }

    @When("Chooses to continue shopping")
    public void chooseToContinueShopping() {
        log.info("[ACTION] Clicking Continue Shopping");
        productsPage.clickContinueShopping();
    }

    @When("User views the cart clicking on the button in the modal")
    public void viewTheCart() {
        log.info("[ACTION] Viewing the cart from modal");
        productsPage.clickViewCartFromModal();
    }
}
