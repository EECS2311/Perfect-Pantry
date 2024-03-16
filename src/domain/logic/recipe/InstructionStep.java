package domain.logic.recipe;

/**
 * Represents a single step in a recipe's instruction, containing the step number and description.
 */
public class InstructionStep {
    private int number;
    private String step;

    /**
     * Constructs an InstructionStep with the specified step number and description.
     *
     * @param number the step number
     * @param step   the step description
     */
    public InstructionStep(int number, String step) {
        this.number = number;
        this.step = step;
    }

    /**
     * Gets the step number.
     *
     * @return the step number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the step number.
     *
     * @param number the step number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the step description.
     *
     * @return the step description
     */
    public String getStep() {
        return step;
    }

    /**
     * Sets the step description.
     *
     * @param step the step description to set
     */
    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "InstructionStep{" +
                "number=" + number +
                ", step='" + step + '\'' +
                '}';
    }
}