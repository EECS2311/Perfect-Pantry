package main.java.domain.logic.recipe;

/**
 * Exception thrown when the API request rate limit per minute is exceeded.
 */
public class RateLimitPerMinuteExceededException extends Exception {
    /**
     * Constructs a new RateLimitPerMinuteExceededException with a default message.
     */
    public RateLimitPerMinuteExceededException() {
        super("API request rate limit per minute exceeded.");
    }
}