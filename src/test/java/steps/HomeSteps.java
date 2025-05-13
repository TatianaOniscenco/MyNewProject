package steps;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pages.HomePage;

public class HomeSteps {
    HomePage homePage = new HomePage(Hooks.getPage());

    @Given("Homepage is loaded")
    public void isOnTheHomePage() {
        homePage.openHomePage();
    }

    @And("User navigates to login page")
    public void navigateToLoginPage() {
        homePage.goToLoginPage();
    }
}
