import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Player{

    //---------------------------------Fields------------------------------------
    private static ArrayList<Item> inventory;
    private ArrayList<Item> usedItems; //items used by player, checked for monster fights
    private static int hp = 100;
    private static int attackPower = 5;
    private static boolean equipped = false;
    private static String equippedItem = null;

    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------

    public Player( ArrayList<Item> inventory, ArrayList<Item> usedItems) {

        this.inventory = inventory;
        this.usedItems = usedItems;
    }

    //---------------------------------Getter and setters------------------------------------

    public static ArrayList<Item> getInventory() {
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

    //sets the player's HP
    public static void setHP(int x)
    {
        hp = x;
    }

    //gets the player's HP
    public static int getHP()
    {
        return hp;
    }

    //sets the player's AP
    public static void setAttackPower(int x)
    {
        attackPower = x;
    }

    //gets the player's AP
    public static int getAttackPower()
    {
        return attackPower;
    }

    //sets the player's HP
    public static boolean isEquipped()
    {
        return equipped;
    }

    //gets the player's HP
    public static void setEquipped(boolean isEquipped)
    {
        equipped = isEquipped;
    }

    //sets the player's HP
    public static String getEquippedItem()
    {
        return equippedItem;
    }

    //gets the player's HP
    public static void setEquippedItem(String item)
    {
        equippedItem = item;
    }
}
