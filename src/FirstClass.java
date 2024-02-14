
public class FirstClass {

	public static void main(String[] args) {

		System.out.println("Hello World");
		GenericTag<FoodGroup> hi = new GenericTag<>(FoodGroup.GRAINS);
		System.out.println(hi.toString());
		System.out.println(hi.getValue());
		hi.setValue(FoodGroup.PROTEIN);
		System.out.println(hi.toString());
		System.out.println(hi.getValue());


	}

}
