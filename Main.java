public class Main{
    public static void main(String[] args){
        java.io.PrintWriter out = new java.io.PrintWriter(System.out);
        Reader in = new Reader();
        out.println("Enter mode:\n1. tile map editor\n2. test tile map and overlay");
        out.flush();
        int mode = 0;
        try{mode = in.nextInt();}catch(Exception e){e.printStackTrace();}
        if(mode==1){
            Tilemapmgr.makeMap();
            out.print("Enter map name to draw:");
            out.flush();
            try{out.println(Tilemapmgr.draw(in.readLine().strip()));}catch(Exception e){e.printStackTrace();}
        }else if(mode==2){
            Tilemapmgr.loadFromImage("test", "C:\\Users\\shunt\\apcs\\CSQuest\\tilemaps\\test.png");
            RenderedMap map=Tilemapmgr.draw("test");
            int x=0, y=0;
            String input="";
            OUTER:
            while (true) {
                map=Tilemapmgr.draw("test");
                map.overlay(new RenderedMap(2, 4, 'X'), x, y);
                out.println(map);
                out.flush();
                try{input=in.readLine();}catch(Exception e){e.printStackTrace();}
                switch (input) {
                    case "w":
                        y--;
                        break;
                    case "s":
                        y++;
                        break;
                    case "a":
                        x--;
                        break;
                    case "d":
                        x++;
                        break;
                    case "q":
                        break OUTER;
                    default:
                        break;
                }
            }
        }
        out.flush();
    }
}