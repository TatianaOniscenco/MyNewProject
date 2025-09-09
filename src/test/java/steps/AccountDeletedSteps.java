package steps;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.AccountDeletedPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDeletedSteps {
    private final Logger log = LoggerFactory.getLogger(AccountDeletedSteps.class);
    private final Page page = PlaywrightFactory.getPage();
    private final AccountDeletedPage accountDeletedPage = new AccountDeletedPage(page);

    @Then("System displays the {string} message confirming delete")
    public void systemDisplaysTheMessageConfirmingAccountDelete(String accountDeletedMessage) {
        String actualMessage = accountDeletedPage.getAccountDeletedMessage();
        if (actualMessage.equals(accountDeletedMessage)) {
            log.info("[ASSERT] Account deleted message matched: '{}'", actualMessage);
        } else {
            log.error("[ASSERT][FAIL] Account deleted message mismatch — Expected: '{}', Actual: '{}'",
                    accountDeletedMessage, actualMessage);
            assertTrue(false, String.format("Expected message: '%s' but got: '%s'", accountDeletedMessage, actualMessage));
        }
    }
}
