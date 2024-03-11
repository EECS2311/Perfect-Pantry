package test;

import domain.logic.ItemUtility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Consumer;

public class ItemUtilityTest {

    @Test
    void testVerifyAddItemWithValidInput() {
        String validName = "Apple";
        String validQuantity = "10";
        String validExpiryDate = "2-oct-2024";

        Consumer<String> errorHandler = errorMsg -> fail("Should not get an error with valid input: " + errorMsg);

        boolean result = ItemUtility.verifyAddItem(validName, validQuantity, validExpiryDate, errorHandler);

        assertTrue(result, "Expected true for valid input parameters.");
    }

    @Test
    void testVerifyAddItemWithEmptyName() {
        String invalidName = "";
        String validQuantity = "5";
        String validExpiryDate = "20-june-2023";

        boolean[] errorOccurred = {false};
        Consumer<String> errorHandler = errorMsg -> errorOccurred[0] = true;

        boolean result = ItemUtility.verifyAddItem(invalidName, validQuantity, validExpiryDate, errorHandler);

        assertFalse(result, "Expected false due to empty item name.");
        assertTrue(errorOccurred[0], "Expected an error due to empty item name.");
    }

    @Test
    void testVerifyAddItemWithInvalidQuantity() {
        String validName = "Banana";
        String invalidQuantity = "not a number";
        String validExpiryDate = "5-may-2024";

        boolean[] errorOccurred = {false};
        Consumer<String> errorHandler = errorMsg -> errorOccurred[0] = true;

        boolean result = ItemUtility.verifyAddItem(validName, invalidQuantity, validExpiryDate, errorHandler);

        assertFalse(result, "Expected false due to invalid quantity.");
        assertTrue(errorOccurred[0], "Expected an error due to invalid quantity.");
    }

    @Test
    void testVerifyAddItemWithNegativeQuantity() {
        String validName = "Carrot";
        String invalidQuantity = "-1";
        String validExpiryDate = "10-aug-2024";

        boolean[] errorOccurred = {false};
        Consumer<String> errorHandler = errorMsg -> errorOccurred[0] = true;

        boolean result = ItemUtility.verifyAddItem(validName, invalidQuantity, validExpiryDate, errorHandler);

        assertFalse(result, "Expected false due to negative quantity.");
        assertTrue(errorOccurred[0], "Expected an error due to negative quantity.");
    }

    @Test
    void testRetrieveStorageTipWithKnownFood() {
        // Assuming "Apple" is a known food with a storage tip in the mock or simulated database
        String foodName = "Apple";
        String expectedTip = "Apples will keep for a week at room temperature, or several weeks in the fruit drawer.";

        String actualTip = ItemUtility.retrieveStorageTip(foodName);

        assertNotNull(actualTip, "Storage tip should not be null for known food items.");
        assertEquals(expectedTip, actualTip, "Storage tip does not match expected value.");
    }

    @Test
    void testRetrieveStorageTipWithUnknownFood() {
        String foodName = "this is not a food";
        String actualTip = ItemUtility.retrieveStorageTip(foodName);
        assertNull(actualTip, "Storage tip should be null for unknown or unsupported food items.");
    }
}
