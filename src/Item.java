import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class Item implements Comparable<Item>{
    private String name;
    private Set<GenericTag<FoodGroup>> foodGroupTags;
    private GenericTag<FoodFreshness> foodFreshnessTag;
    private int quantity;

    private Date expiryDate;

    private Item(String name, int quantity, Date expiryDate){
        this(name, quantity);
        this.expiryDate = expiryDate;
    }
    private Item(String name){
        this.name = name;
    }
    private Item(String name, int quantity){
        this(name);
        this.quantity = quantity;
    }

    public static Item getInstance(String name, Set<GenericTag<FoodGroup>> foodGroupTags, GenericTag<FoodFreshness> foodFreshnessTag, int quantity, Date expiryDate){
        Item item = Item.getInstance(name, quantity, expiryDate);
        item.setFoodGroupTags(foodGroupTags);
        item.setFoodFreshnessTag(foodFreshnessTag);
        return item;
    }
    public static Item getInstance(String name, Set<GenericTag<FoodGroup>> foodGroupTags, GenericTag<FoodFreshness> foodFreshnessTag, int quantity, String expiryDate){
        Date date = parseDate(expiryDate);
        return getInstance(name, foodGroupTags, foodFreshnessTag, quantity, date);
    }

    public static Item getInstance(String name, Set<FoodGroup> foodGroups, FoodFreshness foodFreshness, int quantity, Date expiryDate) {
        Item item = Item.getInstance(name, quantity, expiryDate);
        item.setFoodGroupTagsEnum(foodGroups);
        item.setFoodFreshnessTag(foodFreshness);
        return item;
    }
    public static Item getInstance(String name, Set<FoodGroup> foodGroups, FoodFreshness foodFreshness, int quantity, String expiryDate) {
        Date date = parseDate(expiryDate);
        return getInstance(name, foodGroups, foodFreshness, quantity, date);
    }

    public static Item getInstance(String name, int quantity){
        if(quantity <= 0) throw new IllegalArgumentException();
        return new Item(name, quantity);
    }
    public static Item getInstance(String name){
        return new Item(name);
    }

    public static Item getInstance(String name, int quantity, Date expiryDate){
        Item temp = Item.getInstance(name, quantity);
        temp.setExpiryDate(expiryDate);
        return temp;
    }
    public static Item getInstance(String name, int quantity, String expiryDate){
        Date date = parseDate(expiryDate);
        return new Item(name, quantity, date);
    }

    // Helper method to parse date strings
    private static Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format: " + dateString);
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<GenericTag<FoodGroup>> getFoodGroupTags() {
        Set<GenericTag<FoodGroup>> temp = new HashSet<>();
        for(GenericTag<FoodGroup> tag: this.foodGroupTags){
            temp.add(new GenericTag<>(tag.getValue()));
        }
        return temp;
    }
    public void setFoodGroupTags(Set<GenericTag<FoodGroup>> foodGroupTags) {
        this.foodGroupTags.clear();
        for(GenericTag<FoodGroup> tag: foodGroupTags){
            this.foodGroupTags.add(new GenericTag<>(tag));
        }
    }
    public void setFoodGroupTagsEnum(Set<FoodGroup> foodGroupTags) {
        this.foodGroupTags.clear();
        if(foodGroupTags != null){
            for(FoodGroup tag: foodGroupTags){
                this.foodGroupTags.add(new GenericTag<>(tag));
            }
        }
    }

    public GenericTag<FoodFreshness> getFoodFreshnessTag() {
        return new GenericTag<>(this.foodFreshnessTag);
    }
    public void setFoodFreshnessTag(GenericTag<FoodFreshness> tag) {
        this.foodFreshnessTag = new GenericTag<>(tag);
    }
    public void setFoodFreshnessTag(FoodFreshness tag) {
        this.foodFreshnessTag = new GenericTag<>(tag);
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if(quantity <= 0) throw new IllegalArgumentException();
        this.quantity = quantity;
    }

    public Date getExpiryDate() {
        return new Date(expiryDate.getTime());
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = new Date(expiryDate.getTime());
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate= parseDate(expiryDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(expiryDate, item.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expiryDate);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", foodGroupTags=" + foodGroupTags +
                ", foodFreshnessTag=" + foodFreshnessTag +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public int compareTo(Item other) {
        // Handle null expiryDate
        if (expiryDate == null && other.expiryDate != null) return -1;
        if (expiryDate != null && other.expiryDate == null) return 1;
        if (expiryDate == null && other.expiryDate == null) return 0;
        // Compare by expiryDate when both are not null
        int dateComparison = expiryDate.compareTo(other.expiryDate);
        if (dateComparison != 0) return dateComparison;

        // Handle null name
        if (name == null && other.name != null) return -1;
        if (name != null && other.name == null) return 1;
        if (name == null && other.name == null) return 0;
        // Compare by name when both are not null
        return name.compareTo(other.name);
    }
}
