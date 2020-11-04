import java.util.HashMap;

public class Map {
    HashMap map = new HashMap<Integer,Room>();

    public Map(HashMap map) {
        this.map = map;
    }

    public Map() {
        this.map = map;
    }

    public HashMap getMap() {
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }
}
