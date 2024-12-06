
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphicsmgr {
    final boolean isGUI;
    JPanel panel;
    JFrame frame;
    HashMap<String, javax.swing.JComponent> components = new HashMap<>();
    public Graphicsmgr(boolean isGUI){
        this.isGUI = isGUI;
    }
    public void makePanel(){
        if(isGUI){
            panel = new JPanel();
        }
    }
    public void makePanel(String name){
        if(isGUI){
            panel = new JPanel();
            frame = new JFrame(name);
            frame.add(panel);
        }
    }
    @Deprecated
    public void makeTextArea(){
        if(isGUI){
            javax.swing.JTextArea textArea = new javax.swing.JTextArea();
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 30));
            components.put("textArea", textArea);
            panel.add(textArea);
        }
    }
    public void makeTextArea(String name){
        if(isGUI){
            javax.swing.JTextArea textArea = new javax.swing.JTextArea();
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 30));
            components.put(name, textArea);
            panel.add(textArea);
        }
    }
    public JComponent getComponent(String name){
        return components.get(name);
    }
    public void draw(){
        if(isGUI){
            drawGUI();
        }else{
            drawTUI();
        }
    }
    private void drawGUI(){
        System.out.println("Drawing GUI");
    }
    private void drawTUI(){
        components.forEach((k, v) -> {
            if(v instanceof javax.swing.JTextArea textArea){
                if(k.equals("tilemap")){
                    RenderedMap rm = Tilemapmgr.draw("test", 0, 0, 10, 10);
                    rm.overlay(new RenderedMap(2, 4, 'X'), 4, 5);
                    textArea.setText(rm.toString());
                };
            }
        });
    }
}
