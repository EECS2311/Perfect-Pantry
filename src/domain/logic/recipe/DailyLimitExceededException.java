package domain.logic.recipe;

public class DailyLimitExceededException extends Exception {
    public DailyLimitExceededException() {
        super("Daily API request limit exceeded.");
    }
}