import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Item implements Comparable<Item>{
    private String name;
    private Set<GenericTag<FoodGroup>> foodGroupTags;
    private GenericTag<FoodFreshness> foodFreshnessTag;
    private int quantity;

    private Date expiryDate;

    private Item(String name, Set<GenericTag<FoodGroup>> foodGroupTags,  GenericTag<FoodFreshness> foodFreshnessTag, int quantity, Date expiryDate){
        this(name, quantity, expiryDate);

        this.foodGroupTags = new HashSet<>();
        for(GenericTag<FoodGroup> tag: foodGroupTags){
            this.foodGroupTags.add(new GenericTag<>(tag));
        }

        this.foodFreshnessTag = new GenericTag<>(foodFreshnessTag);
    }

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
        return new Item(name, foodGroupTags, foodFreshnessTag, quantity, expiryDate);
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
        this.foodGroupTags = new HashSet<>();
        for(GenericTag<FoodGroup> tag: foodGroupTags){
            this.foodGroupTags.add(new GenericTag<>(tag));
        }
    }

    public void setFoodGroupTagsEnum(Set<FoodGroup> foodGroupTags) {
        this.foodGroupTags = new HashSet<>();
        for(FoodGroup tag: foodGroupTags){
            this.foodGroupTags.add(new GenericTag<>(tag));
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
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


    // TODO: Figure out which attributes should be taken into consideration
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
        // First compare by expiryDate
        int dateComparison = expiryDate.compareTo(other.expiryDate);
        if (dateComparison != 0) {
            return dateComparison;
        }
        // If expiryDate is the same, then compare by name
        return name.compareTo(other.name);
    }
}
