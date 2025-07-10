package factory;

import ENUM.BrowserName;
import com.microsoft.playwright.*;
import config.ConfigReader;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void initBrowser(BrowserName browserName) {
        playwright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(ConfigReader.get("headless")));

        Browser launchedBrowser = launchBrowser(browserName, launchOptions);
        browser.set(launchedBrowser);

        BrowserContext browserContext = launchedBrowser.newContext();
        context.set(browserContext);

        Page newPage = browserContext.newPage();
        page.set(newPage);
    }

    private static Browser launchBrowser(BrowserName browserName, BrowserType.LaunchOptions options) {
        Playwright pw = playwright.get();

        return switch (browserName) {
            case FIREFOX -> pw.firefox().launch(options);
            case WEBKIT -> pw.webkit().launch(options);
            case CHROMIUM -> pw.chromium().launch(options);
        };
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

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

    private static void closeSafely(AutoCloseable resource, String name) {
        try {
            if (resource != null) resource.close();
        } catch (Exception e) {
            System.err.println("[PlaywrightFactory] Failed to close " + name + ": " + e.getMessage());
        }
    }
}