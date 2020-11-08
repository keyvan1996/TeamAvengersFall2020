import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name ="Robert";
    public int attackPoints = 2;
    ArrayList<Item> inventory = new ArrayList();
    public int health = 100;
    private int currentRoomID = 0;
    ArrayList<Item> equippedItems = new ArrayList<>();

    public Player(String name, int attackPoints, int health, int currentRoomID) {
        this.name = name;
        this.attackPoints = attackPoints;
        this.inventory = inventory;
        this.health = health;
        this.currentRoomID = currentRoomID;
    }

    public Player() {
        this.name = name;
        this.attackPoints = attackPoints;
        this.inventory = inventory;
        this.health = health;
        this.currentRoomID = currentRoomID;
    }

    public int getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(int currentRoomID) {
        this.currentRoomID = currentRoomID;
    }

    public ArrayList<Item> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(ArrayList<Item> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
