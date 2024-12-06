public class Location {
    Scene scene;
    Position position;
    public Location(Scene scene, Position position) {
        this.scene = scene;
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
    public Scene getScene() {
        return scene;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
