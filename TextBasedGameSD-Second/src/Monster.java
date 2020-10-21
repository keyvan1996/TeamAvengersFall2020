public class Monster {

    private int id;
    private String name;
    private String description;
    private int hp;
    private String action;

    public Monster(int id, String name, String description, int hp, String action){
        this.id = id;
        this.name = name;
        this.description = description;
        this.hp = hp;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
