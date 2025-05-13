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
        page.navigate("https://automationexercise.com/products");
        page.waitForLoadState();
    }

    @When("adds {string} product to the cart")
    public void addProductToCart(String productName) {
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
    }

    @And("chooses to continue shopping")
    public void chooseToContinueShopping() {
        productsPage.clickContinueShopping();
    }

    @And("user views the cart")
    public void ViewTheCart() {
        productsPage.clickViewCartFromModal();
    }

}
