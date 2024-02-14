import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FirstClass {

	public static void main(String[] args) {

		System.out.println("Hello World");

		Set<GenericTag<FoodGroup>> tags = new HashSet<>();
		tags.add(new GenericTag<>(FoodGroup.DAIRY));
		Date day = new Date();
		Item i = Item.getInstance("Milk", tags, new GenericTag<>(FoodFreshness.FRESH), 5, day);
		System.out.println(i);

	}

}
