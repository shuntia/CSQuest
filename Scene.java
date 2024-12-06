
import java.util.ArrayList;

public class Scene {
    String name;
    Tilemap tilemap;
    ArrayList<MapCharacter> characters = new ArrayList<>();
    public Scene(String name) {
        this.name = name;
    }
    public Scene(String name, Tilemap tilemap) {
        this.name = name;
        this.tilemap = tilemap;
    }
    public void addCharacter(MapCharacter character) {
        characters.add(character);
    }
    public void removeCharacter(MapCharacter character) {
        characters.remove(character);
    }
    public void setTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }
    public Tilemap getTilemap() {
        return tilemap;
    }
}
