import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Player{

    //---------------------------------Fields------------------------------------
    private ArrayList<Item> inventory;
    private ArrayList<Item> usedItems; //items used by player, checked for monster fights

    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------

    public Player( ArrayList<Item> inventory, ArrayList<Item> usedItems) {

        this.inventory = inventory;
        this.usedItems = usedItems;
    }

    //---------------------------------Getter and setters------------------------------------

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Item> getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(ArrayList<Item> usedItems) {
        this.usedItems = usedItems;
    }
}
