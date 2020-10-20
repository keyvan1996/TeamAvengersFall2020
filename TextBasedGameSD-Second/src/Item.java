import java.util.ArrayList;

public class Item {
    //---------------------------------Fields------------------------------------
    private String name;
    private String description;
    private String pickup;
    private String heal;
    private String damage;
    private boolean equipped;
    private boolean consume;
    private static ArrayList<Item> itemItems = new ArrayList<Item>();
    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------

    public Item(String name, String description, String pickup, String heal, String damage,
                boolean equipped, boolean consume) {
        this.name = name;
        this.description = description;
        this.pickup = pickup;
        this.heal = heal;
        this.damage = damage;
        this.equipped = equipped;
        this.consume = consume;

    }


    //---------------------------------Getter and setters------------------------------------


    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getHeal() {
        return heal;
    }

    public void setHeal(String heal) {
        this.heal = heal;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public boolean isConsume()
    {
        return consume;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public static ArrayList<Item> getItemItems() {
        return itemItems;
    }

    public static void setItemItems(ArrayList<Item> itemItems) {
        Item.itemItems = itemItems;
    }


}
