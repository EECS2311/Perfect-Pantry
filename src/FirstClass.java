import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FirstClass {

	public static void main(String[] args) {

		System.out.println("Hello World");
		GenericTag<FoodGroup> group1 = new GenericTag<>(FoodGroup.GRAINS);
		GenericTag<FoodGroup> group2 = new GenericTag<>(FoodGroup.GRAINS);
		GenericTag<FoodGroup> group3 = new GenericTag<>(FoodGroup.PROTEIN);

		System.out.println(group1.equals(group2.hashCode()));
		System.out.println(group1.equals(group1));
		System.out.println(group1.equals(group3));

		System.out.println(group1.hashCode() == group2.hashCode());
		System.out.println(group1.hashCode() == group1.hashCode());
		System.out.println(group1.hashCode() == group3.hashCode());

//		System.out.println(group1.toString());
//		System.out.println(group1.getValue());
//		group1.setValue(FoodGroup.PROTEIN);
//		System.out.println(group1.toString());
//		System.out.println(group1.getValue());
		GenericTag<FoodFreshness> fresh = new GenericTag<>(FoodFreshness.FRESH);
		System.out.println(fresh.toString());
		System.out.println(fresh.getValue());
		fresh.setValue(FoodFreshness.EXPIRED);
		System.out.println(fresh.toString());
		System.out.println(fresh.getValue());

		Set<GenericTag<FoodGroup>> FoodGroupTags = new HashSet<>();


		FoodGroupTags.add(group1);
		FoodGroupTags.add(group1);
		FoodGroupTags.add(group2);

		System.out.println(FoodGroupTags.toString());



	}

}
