package helpers;

import com.microsoft.playwright.Page;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

public class ScreenshotHelper {

    private ScreenshotHelper() {
        throw new UnsupportedOperationException("Utility class — cannot be instantiated.");
    }

    /**
     * Captures screenshot to disk, and attaches to Allure if test fails.
     */
    public static void capture(Page page, Path path, Scenario scenario) {
        if (page == null || path == null) return;

        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        Path screenshotPath = path.resolve(result + ".png");

        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));

        if (scenario.isFailed()) {
            Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
        }
    }
}
