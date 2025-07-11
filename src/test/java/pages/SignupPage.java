package pages;

import com.microsoft.playwright.Page;

public class SignupPage {

    private final Page page;

    // Locators
    private static final String PASSWORD_FIELD = "[data-qa='password']";
    private static final String FIRST_NAME_FIELD = "[data-qa='first_name']";
    private static final String LAST_NAME_FIELD = "[data-qa='last_name']";
    private static final String ADDRESS_FIELD = "[data-qa='address']";
    private static final String COUNTRY_DROPDOWN = "[data-qa='country']";
    private static final String STATE_FIELD = "[data-qa='state']";
    private static final String CITY_FIELD = "[data-qa='city']";
    private static final String ZIP_CODE_FIELD = "[data-qa='zipcode']";
    private static final String MOBILE_NUMBER_FIELD = "[data-qa='mobile_number']";
    private static final String CREATE_ACCOUNT_BUTTON = "[data-qa='create-account']";
    private static final String ENTER_ACCOUNT_INFO_HEADING = "text=Enter Account Information";
    private static final String USER_EXISTS_MESSAGE = "text=Email Address already exist!";

    // Constructor
    public SignupPage(Page page) {
        this.page = page;
    }

    // Actions
    public void enterPassword(String password) {
        page.locator(PASSWORD_FIELD).fill(password);
    }

    public void enterFirstName(String firstName) {
        page.locator(FIRST_NAME_FIELD).fill(firstName);
    }

    public void enterLastName(String lastName) {
        page.locator(LAST_NAME_FIELD).fill(lastName);
    }

    public void enterAddress(String address) {
        page.locator(ADDRESS_FIELD).fill(address);
    }

    public void selectCountry(String country) {
        page.locator(COUNTRY_DROPDOWN).selectOption(country);
    }

    public void enterState(String state) {
        page.locator(STATE_FIELD).fill(state);
    }

    public void enterCity(String city) {
        page.locator(CITY_FIELD).fill(city);
    }

    public void enterZipCode(String zipCode) {
        page.locator(ZIP_CODE_FIELD).fill(zipCode);
    }

    public void enterMobileNumber(String phoneNumber) {
        page.locator(MOBILE_NUMBER_FIELD).fill(phoneNumber);
    }

    public void clickCreateAccountButton() {
        page.locator(CREATE_ACCOUNT_BUTTON).click();
    }

    public boolean isEnterAccountInfoVisible() {
        return page.locator(ENTER_ACCOUNT_INFO_HEADING).isVisible();
    }

    public String getExistingUserMessage() {
        return page.locator(USER_EXISTS_MESSAGE).textContent().trim();
    }
}
