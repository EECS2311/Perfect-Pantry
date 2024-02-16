import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagUtility {

    public static <E extends Enum<E> & Tag> List<E> getAllEnumValues(Class<E> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    public static <E extends Enum<E> & Tag> List<String> getAllDisplayNames(Class<E> enumClass) {
        List<String> displayNames = new ArrayList<>();
        for (E enumConstant : enumClass.getEnumConstants()) {
            displayNames.add(enumConstant.getDisplayName());
        }
        return displayNames;
    }
}