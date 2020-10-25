import java.util.ArrayList;

public class Room {
    //---------------------------------Fields------------------------------------
    private int id;
    private String name;
    private String description;
    private String dirs;
    private Boolean isVisited;
    private ArrayList<Item> itemsInRoom;
    private Puzzle puzzle;
    private String floor;
    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------


    public Room(int id, String name, String description, String dirs, Boolean isVisited, ArrayList<Item> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dirs = dirs;
        this.isVisited = isVisited;
        this.itemsInRoom = items;
    }

    //---------------------------------Getter and setters------------------------------------


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

    public String getDirs() {
        return dirs;
    }

    public void setDirs(String dirs) {
        this.dirs = dirs;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

    public ArrayList<Item> getItemsInRoom() {
        return itemsInRoom;
    }

    public void setItemsInRoom(ArrayList<Item> itemsInRoom) {
        this.itemsInRoom = itemsInRoom;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
