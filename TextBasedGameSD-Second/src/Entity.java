import java.io.Serializable;

public class Entity implements Serializable{

    //instance variables
    private String entityName;
    private int health;

    //constructor
    public Entity(String entityName, int health) {
        super();
        this.entityName = entityName;
        this.health = health;
    }

    //getters and setters
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}