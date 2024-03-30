package domain.logic.recipe;

public class RateLimitPerMinuteExceededException extends Exception {
    public RateLimitPerMinuteExceededException() {
        super("API request rate limit per minute exceeded.");
    }
}