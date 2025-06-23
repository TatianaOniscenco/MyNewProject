package factory;

import com.microsoft.playwright.*;
import config.ConfigReader;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void initBrowser() {
        // Read browser name and headless mode from config.properties
        String browserName = ConfigReader.get("browser");
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless"));

        // Create a new Playwright instance
        playwright.set(Playwright.create());

        // Set launch options
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless);

        // Launch the specified browser
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser.set(playwright.get().firefox().launch(options));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(options));
                break;
            case "chromium":
            case "chrome": // accepted alias
            default:
                browser.set(playwright.get().chromium().launch(options));
                break;
        }

        // Create browser context and page
        context.set(browser.get().newContext());
        page.set(context.get().newPage());
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    public static void close() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();

        // Remove thread-local instances
        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }
}