package context;

import java.util.HashMap;
import java.util.Map;

// ScenarioContext is a custom context holder that acts like a map (HashMap<String, Object>).
// It’s used to store and retrieve scenario-specific data across step definitions in a thread-safe and isolated manner.
// Context Object Pattern – centralizes and encapsulates scenario-specific data.
// Not a Singleton by itself, but its lifecycle is per thread (handled by ScenarioContextManager).
public class ScenarioContext {

    // A map to store key-value pairs for test scenario data.
    // Key = descriptive name, Value = any object (User, Token, etc.)
    private final Map<String, Object> context = new HashMap<>();

    /**
     * Stores a value in the context for the given key.
     *
     * @param key   The name under which the value is stored.
     * @param value The actual object to store.
     * @param <T>   The type of the value.
     */
    public <T> void set(String key, T value) {
        context.put(key, value);
    }

    /**
     * Retrieves a value from the context and casts it to the expected type.
     * Note: This cast is unchecked, so it may throw a ClassCastException at runtime if misused.
     *
     * @param key The key whose associated value is to be returned.
     * @param <T> The expected return type.
     * @return The object associated with the key, cast to type T.
     */
    @SuppressWarnings("unchecked") // Cast is unchecked, so you must know the correct type when retrieving
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    /**
     * Checks if a value is stored under the given key.
     *
     * @param key The key to check.
     * @return True if the context contains the key, false otherwise.
     */
    public boolean contains(String key) {
        return context.containsKey(key);
    }

    /**
     * Returns a string representation of the stored data for logging or debugging.
     *
     * @return String showing all key-value pairs in the context.
     */
    @Override
    public String toString() {
        return "ScenarioContext{" + "context=" + context + '}';
    }
}