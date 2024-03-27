package domain.logic;

import java.util.ArrayList;

public class StatsUtilities {
	
	
	public static int getTotalFoodGroup(String group, ArrayList<String> s) {
		
		return s.stream().filter(x -> x!= null && x.equals(group)).toList().size();
		
		
	}
	
	public static String getPercent(ArrayList<String> allItems, int total, String group) {
		
		return String.format("%2.1f", ((StatsUtilities.getTotalFoodGroup(group, allItems)+0.0))/total*100);
		
	}

}
