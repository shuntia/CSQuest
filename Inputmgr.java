import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import java.util.HashSet;
import java.util.ArrayList;

public class Inputmgr implements EventHandler<KeyEvent> {
    private static HashSet<String> keys = new HashSet<>();
    private ArrayList<KeyResponsive> keyResponsives = new ArrayList<>();

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            keyPressed(event);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            keyReleased(event);
        } else if (event.getEventType() == KeyEvent.KEY_TYPED) {
            keyTyped(event);
        }
    }

    public void keyTyped(KeyEvent e) {
        for (KeyResponsive kr : keyResponsives) {
            kr.keyTyped(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getCode().toString());
        String keyName = e.getCode().toString();
        keys.add(keyName);
        for (KeyResponsive kr : keyResponsives) {
            kr.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        String keyName = e.getCode().toString();
        keys.remove(keyName);
        for (KeyResponsive kr : keyResponsives) {
            kr.keyReleased(e);
        }
    }

    public static HashSet<String> getKeys() {
        return keys;
    }

    public void addKeyResponsive(KeyResponsive kr) {
        keyResponsives.add(kr);
    }
}