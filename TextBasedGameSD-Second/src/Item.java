public class Item {
    //---------------------------------Fields------------------------------------
    private String name;
    private String description;
    private String pickup;
    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------


    public Item(String name, String description, String pickup) {
        this.name = name;
        this.description = description;
        this.pickup = pickup;

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

}
