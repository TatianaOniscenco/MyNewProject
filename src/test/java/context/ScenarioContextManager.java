package context;

// Utility class ScenarioContextManager implemented using ThreadLocal,
// so each scenario has its own clean instance of ScenarioContext.
// Ensures scenario data like users, tokens, etc., are isolated between threads.
public class ScenarioContextManager {

    private ScenarioContextManager() {} // prevent instantiation

    private static final ThreadLocal<ScenarioContext> scenarioContext = ThreadLocal.withInitial(ScenarioContext::new);

    public static ScenarioContext get() {
        return scenarioContext.get();
    }

    public static void reset() {
        scenarioContext.remove();
    }
}
