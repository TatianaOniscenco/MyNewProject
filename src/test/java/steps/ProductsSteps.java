package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import pages.ProductsPage;

public class ProductsSteps {
    Page page = Hooks.getPage();
    ProductsPage productsPage = new ProductsPage(page);

    @Given("User is on Products page")
    public void isOnProductsPage() {
        Hooks.logToFile("[ACTION] Navigating to Products page");
        page.navigate("https://automationexercise.com/products");
        page.waitForLoadState();
    }

    @When("adds {string} product to the cart")
    public void addProductToCart(String productName) {
        Hooks.logToFile("[ACTION] Adding product to cart: " + productName);
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
    }

    @And("chooses to continue shopping")
    public void chooseToContinueShopping() {
        Hooks.logToFile("[ACTION] Clicking Continue Shopping");
        productsPage.clickContinueShopping();
    }

    @And("user views the cart")
    public void viewTheCart() {
        Hooks.logToFile("[ACTION] Viewing the cart from modal");
        productsPage.clickViewCartFromModal();
    }
}
