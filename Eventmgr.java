
import java.util.HashMap;

public class Eventmgr {
    HashMap<String, Integer> flags = new HashMap<>();
    public void addEvent(String name, int value) {
        flags.put(name, value);
    }
    public void removeEvent(String name) {
        flags.remove(name);
    }
    public void setEventValue(String name, int value) {
        flags.put(name, value);
    }
    public int getEventValue(String name) {
        return flags.get(name);
    }
    // If event is 1, checkEvent will return true. if value is 0, false otherwise 0
    public boolean checkEvent(String name) {
        return flags.get(name) == 1;
    }
}
