import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Graphicsmgr extends Application implements KeyResponsive {
    private final boolean isGUI;
    private Stage stage;
    private BorderPane rootPane;
    private final HashMap<String, javafx.scene.Node> components = new HashMap<>();
    public Position position = new Position();
    public BufferedImage UILayer, MapLayer, OverlayLayer, frameBuffer;
    public static boolean ismap = true;
    private int dx = 1; // Movement speed in x-direction
    private int dy = 0; // Movement speed in y-direction

    public Graphicsmgr() {
        this.isGUI = true;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        makePane("Graphics Manager");
        // Initialize frameBuffer or other layers as needed
    }

    public void makePane(String name) {
        rootPane = new BorderPane();
        rootPane.setPrefSize(800, 600);

        Scene scene = new Scene(rootPane);
        stage.setTitle(name);
        stage.setScene(scene);
        stage.show();

        // Setup Input Manager
        Inputmgr inputMgr = new Inputmgr();
        inputMgr.addKeyResponsive(this);
        scene.setOnKeyPressed(inputMgr);
        scene.setOnKeyReleased(inputMgr);
        scene.setOnKeyTyped(inputMgr);
    }

    public void move(int dx, int dy) {
        position.x += dx;
        position.y += dy;
        updateGUI();
    }

    public void updateGUI() {
        if (rootPane == null) {
            throw new IllegalStateException("Pane is not properly initialized.");
        }

        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(frameBuffer, null));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(rootPane.getWidth());
        imageView.setFitHeight(rootPane.getHeight());

        rootPane.setCenter(imageView);
    }

    public void updateTUI() {
        components.forEach((k, v) -> {
            if (v instanceof TextArea textArea) {
                if (k.equals("tilemap")) {
                    RenderedCMap rm = Tilemapmgr.drawcmap("test", position.x, position.y, 10, 10);
                    rm.overlay(new RenderedCMap(2, 4, 'X'), 4, 5);
                    textArea.setText(rm.toString());
                }
            }
        });
    }

    @Override
    public void keyTyped(javafx.scene.input.KeyEvent e) {
        // Handle key typed events
    }

    @Override
    public void keyPressed(javafx.scene.input.KeyEvent e) {
        System.out.println("Key Pressed: " + e.getCode());
        String keyName = e.getCode().toString();
        Inputmgr.getKeys().add(keyName);
        // Additional key pressed handling
    }

    @Override
    public void keyReleased(javafx.scene.input.KeyEvent e) {
        String keyName = e.getCode().toString();
        Inputmgr.getKeys().remove(keyName);
        // Additional key released handling
    }

    public static void main(String[] args) {
        launch(args);
    }
}
