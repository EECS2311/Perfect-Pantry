package domain.logic.recipe;

import gui.HomeView;

import java.util.HashSet;
import java.util.Set;

public class RecipeUtility {
    public static boolean isNearExpiryItemsChanged(Set<String> ingredients){
        Set<String> currentIngredients = new HashSet<String>(HomeView.data.getExpiringItems());
        if(currentIngredients.equals(ingredients)){
            return false;
        } else{
            ingredients = currentIngredients;
            return true;
        }
    }

//    public static String spoonacularIngredientInput(){
//
//    }
}
