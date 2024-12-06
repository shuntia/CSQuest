public class MapCharacter extends Character {
    Location location;
    public MapCharacter(String name) {
        this.name = name;
        location = new Location(null, new Position());
    }
    public MapCharacter(String name, Scene scene, Position position) {
        this.name = name;
        location = new Location(scene, position);
    }
    public MapCharacter(String name, Location location) {
        this.name = name;
        this.location = location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }
    public void setPosition(int x, int y) {
        location.position.x = x;
        location.position.y = y;
    }
}
