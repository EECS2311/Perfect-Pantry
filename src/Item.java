import java.util.Date;
import java.util.Objects;

public class Item {
    private String name;
    private String tag;
    private int quantity;
    private Date expiryDate;

    private Item(String name, String tag, int quantity, Date expiryDate){
        this(name, quantity, expiryDate);
        this.tag = tag;
    }

    private Item(String name, int quantity, Date expiryDate){
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public static Item getInstance(String name, String tag, int quantity, Date expiryDate){
        Item temp = Item.getInstance(name, quantity, expiryDate);
        temp.setTag(tag);
        return temp;
    }

    public static Item getInstance(String name, int quantity, Date expiryDate){
        if(quantity <= 0) throw new IllegalArgumentException();
        return new Item(name, quantity, expiryDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
                ", tag='" + tag + '\'' +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
