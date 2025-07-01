package pages;

import com.microsoft.playwright.Page;
// This class represents the Signup page functionalities in the application.
// It provides methods to interact with the signup form elements and perform actions like filling the form and clicking the create account button.
public class SignupPage {
    private final Page page;

    private final String passwordField = "[data-qa='password']";
    private final String firstNameField = "[data-qa='first_name']";
    private final String lastNameField = "[data-qa='last_name']";
    private final String addressField = "[data-qa='address']";
    private final String countryDropdown = "[data-qa='country']";
    private final String stateField = "[data-qa='state']";
    private final String cityField = "[data-qa='city']";
    private final String zipCodeField = "[data-qa='zipcode']";
    private final String mobileNumberField = "[data-qa='mobile_number']";
    private final String createAccountButton = "[data-qa='create-account']";

    public SignupPage(Page page) {
        this.page = page;
    }

    public void enterPassword(String password) {
        page.locator(passwordField).fill(password);
    }

    public void enterFirstName(String firstName) {
        page.locator(firstNameField).fill(firstName);
    }

    public void enterLastName(String lastName) {
        page.locator(lastNameField).fill(lastName);
    }

    public void enterAddress(String address) {
        page.locator(addressField).fill(address);
    }

    public void selectCountry(String country) {
        page.locator(countryDropdown).selectOption(country);
    }

    public void enterState(String state) {
        page.locator(stateField).fill(state);
    }

    public void enterCity(String city) {
        page.locator(cityField).fill(city);
    }

    public void enterZipCode(String zipcode) {
        page.locator(zipCodeField).fill(zipcode);
    }

    public void enterMobileNumber(String mobileNumber) {
        page.locator(mobileNumberField).fill(mobileNumber);
        page.waitForTimeout(3000); // 3-second pause for realism (optional)
    }

    public void clickCreateAccountButton() {
        page.locator(createAccountButton).click();
    }

    public boolean isEnterAccountInfoVisible() {
        return page.locator("text=Enter Account Information").isVisible();
    }
}
