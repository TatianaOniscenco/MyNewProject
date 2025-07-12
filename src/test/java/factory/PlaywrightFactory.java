package factory;

import ENUM.BrowserName;
import com.microsoft.playwright.*;
import config.ConfigReader;

/**
 * Thread-safe factory class for initializing and managing Playwright components:
 * Playwright, Browser, BrowserContext, and Page.
 */
public class PlaywrightFactory {

    // Thread-local instances to ensure test isolation during parallel execution
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    /**
     * Initializes the Playwright browser instance with the specified browser type.
     * @param browserName The browser to launch (CHROMIUM, FIREFOX, or WEBKIT).
     */
    public static void initBrowser(BrowserName browserName) {
        // Start Playwright
        playwright.set(Playwright.create());

        // Set launch options (e.g., headless mode from config)
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(ConfigReader.getInstance().get("headless")));

        // Launch browser and store in ThreadLocal
        Browser launchedBrowser = launchBrowser(browserName, launchOptions);
        browser.set(launchedBrowser);

        // Create and store context and page
        BrowserContext browserContext = launchedBrowser.newContext();
        context.set(browserContext);

        Page newPage = browserContext.newPage();
        page.set(newPage);
    }

    /**
     * Launches the specified browser with given options.
     */
    private static Browser launchBrowser(BrowserName browserName, BrowserType.LaunchOptions options) {
        Playwright pw = playwright.get();
        return switch (browserName) {
            case FIREFOX -> pw.firefox().launch(options);
            case WEBKIT  -> pw.webkit().launch(options);
            case CHROMIUM, CHROME -> pw.chromium().launch(options);
        };
    }

    /**
     * Returns the current thread's Page instance.
     */
    public static Page getPage() {
        return page.get();
    }

    /**
     * Closes all Playwright-related resources and removes them from ThreadLocal.
     */
    public static void close() {
        closeSafely(page.get(), "page");
        closeSafely(context.get(), "context");
        closeSafely(browser.get(), "browser");
        closeSafely(playwright.get(), "playwright");

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }

    /**
     * Utility method for safely closing any AutoCloseable resource.
     */
    private static void closeSafely(AutoCloseable resource, String name) {
        try {
            if (resource != null) resource.close();
        } catch (Exception e) {
            System.err.println("[PlaywrightFactory] Failed to close " + name + ": " + e.getMessage());
        }
    }
}