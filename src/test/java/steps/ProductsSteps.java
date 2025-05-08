package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsSteps {
    Page page = Hooks.getPage();
    ProductsPage productsPage = new ProductsPage(page);

    @Given("User is on Products page")
    public void userIsOnProductsPage() {
        page.navigate("https://automationexercise.com/products");
        page.waitForLoadState();
    }

    @When("adds {string} product to the cart")
    public void addsProductToCart(String productName) {
        productsPage.hoverOverProductByName(productName);
        productsPage.clickAddToCartByName(productName);
    }

    @And("chooses to continue shopping")
    public void choosesToContinueShopping() {
        productsPage.clickContinueShopping();
    }

    @And("user views the cart")
    public void userViewsTheCart() {
        productsPage.clickViewCartFromModal();
    }

}
