public class Graphicsmgr {
    final boolean isGUI;
    public Graphicsmgr(boolean isGUI){
        this.isGUI = isGUI;
    }
    public void draw(){
        if(isGUI){
            drawGUI();
        }else{
            drawConsole();
        }
    }
    private void drawGUI(){
        System.out.println("Drawing GUI");
    }
    private void drawConsole(){
        System.out.println("Drawing Console");
    }
}
