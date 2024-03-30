package domain.logic.recipe;

/**
 * Exception thrown when the daily API request limit is exceeded.
 */
public class DailyLimitExceededException extends Exception {
    /**
     * Constructs a new DailyLimitExceededException with a default message.
     */
    public DailyLimitExceededException() {
        super("Daily API request limit exceeded.");
    }
}