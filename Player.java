
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class Player {
    static HashMap<String, Image[]> sprites = new HashMap<>();
    public static Position position = new Position();
    static{
        sprites.put("fw", new Image[] {
            new ImageIcon("Sprites/Frisk/fw1").getImage(),
            new ImageIcon("Sprites/Frisk/fw2").getImage(),
            new ImageIcon("Sprites/Frisk/fw3").getImage(),
            new ImageIcon("Sprites/Frisk/fw4").getImage()
        });
        sprites.put("bk", new Image[] {
            new ImageIcon("Sprites/Frisk/bk1").getImage(),
            new ImageIcon("Sprites/Frisk/bk2").getImage(),
            new ImageIcon("Sprites/Frisk/bk3").getImage(),
            new ImageIcon("Sprites/Frisk/bk4").getImage()
        });
        sprites.put("lt", new Image[] {
            new ImageIcon("Sprites/Frisk/lt1").getImage(),
            new ImageIcon("Sprites/Frisk/lt2").getImage()
        });
        sprites.put("rt", new Image[] {
            new ImageIcon("Sprites/Frisk/rt1").getImage(),
            new ImageIcon("Sprites/Frisk/rt2").getImage()
        });
    }
    public static Image getSprite(String direction, int frame){
        return sprites.get(direction)[frame];
    }
}
