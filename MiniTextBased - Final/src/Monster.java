import java.util.ArrayList;

public class Monster{
    private int roomId;
    private int monsterID;
    private String name;
    private int health;
    private int attackPoints;
    private ArrayList<Item> inventory = new ArrayList();
    private String description;

    public Monster(int monsterID, String name, int health, int attackPoints, ArrayList<Item> inventory, int roomId, String description) {
        this.monsterID = monsterID;
        this.name = name;
        this.health = health;
        this.attackPoints = attackPoints;
        this.inventory = inventory;
        this.roomId = roomId;
        this.description = description;
    }

    public Monster() {
        this.monsterID = monsterID;
        this.name = name;
        this.health = health;
        this.attackPoints = attackPoints;
        this.inventory = inventory;
        this.roomId= roomId;
        this.description= description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getMonsterID() {
        return monsterID;
    }

    public void setMonsterID(int monsterID) {
        this.monsterID = monsterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
}
