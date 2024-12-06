
import java.io.*;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Tilemapmgr {
    static HashMap<String,Tilemap> tilemaps = new HashMap<>();
    public static final char[] tilechars = {' ','.','~','-','|','/'};
    static boolean active = false;
    public static void loadFromImage(String name, String imagePath){
        try{
            // Load the image
            java.awt.image.BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] rg = new int[height][width];
            int[][] b = new int[height][width];
            // Calculate pixel brightness
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    
                    // Extract RGB components
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;
                    
                    // Sum RGB values
                    int brightness = red + green;
                    rg[y][x] = brightness; // Store in the array
                    b[y][x] = blue; // Store in the array
                }
            }
            // Create tilemap
            Tilemap map = new Tilemap(rg,b);
            tilemaps.put(name, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadMap(String name, String path){
        try(DataInputStream dis = new DataInputStream(new FileInputStream(path))){
            int width = dis.readInt();
            int height = dis.readInt();
            int[][] tileids = new int[width][height];
            int[][] tiledata = new int[width][height];
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    tileids[x][y] = dis.readInt();
                    tiledata[x][y] = dis.readInt();
                }
                if(dis.readInt()!=0){
                    throw new IOException("Failed to load map: "+name+" (Invalid file format or alignment)");
                }
            }
            if(dis.available()!=0){
                throw new IOException("Failed to load map: "+name+" (Invalid file format or alignment)");
            }
            Tilemap map = new Tilemap(tileids, tiledata);
            tilemaps.put(name, map);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void loadMap(String name, Tilemap map){
        tilemaps.put(name, map);
    }
    public static void saveMap(String name, String path){
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(path))){
            Tilemap map = tilemaps.get(name);
            dos.writeInt(map.tileids.length);
            dos.writeInt(map.tileids[0].length);
            for(int x = 0; x < map.tileids.length; x++){
                for(int y = 0; y < map.tileids[0].length; y++){
                    dos.writeInt(map.tileids[x][y]);
                    dos.writeInt(map.tiledata[x][y]);
                }
            }
            dos.writeInt(0);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void makeMap(){
        Reader in = new Reader();
        java.io.PrintWriter out = new java.io.PrintWriter(System.out);
        out.println("Enter map name:");
        out.flush();
        String name = "";
        try{name = in.readLine();}catch(IOException e){e.printStackTrace();}
        out.println("Enter map width:");
        out.flush();
        int width = 0;
        try{width = in.nextInt();}catch(IOException e){e.printStackTrace();}
        out.println("Enter map height:");
        out.flush();
        int height = 0;
        try{height = in.nextInt();}catch(IOException e){e.printStackTrace();}
        if(name.equals("empty")){
            out.println("Enter tile id and data for empty tile:");
            out.flush();
            try{tilemaps.put(name, new Tilemap(width, height, in.nextInt(), in.nextInt()));}catch(IOException e){e.printStackTrace();}
            return;
        }
        int[][] tileids = new int[width][height];
        int[][] tiledata = new int[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                out.println("Enter tile id and data for tile at "+x+","+y+":");
                out.flush();
                try{tileids[x][y] = in.nextInt();}catch(IOException e){e.printStackTrace();}
                try{tiledata[x][y] = in.nextInt();}catch(IOException e){e.printStackTrace();}
            }
        }
        tilemaps.put(name, new Tilemap(tileids, tiledata));
    }
    public static RenderedMap draw(String name){
        char[][] ret = new char[tilemaps.get(name).tileids.length][tilemaps.get(name).tileids[0].length];
        Tilemap map = tilemaps.get(name);
        for(int x=0;x<map.tileids.length;x++){
            for(int y=0;y<map.tileids[0].length;y++){
                ret[x][y] = tilechars[map.tileids[x][y]];
            }
        }
        return new RenderedMap(ret);
    }
    public static RenderedMap draw(String name, int x, int y, int width, int height){
        char[][] ret = new char[height][width];
        int mapwidth = tilemaps.get(name).tileids.length, mapheight = tilemaps.get(name).tileids[0].length;
        Tilemap map = tilemaps.get(name);
        for(int ox=0;ox<width;ox++){
            for(int oy=0;oy<height;oy++){
                ret[oy][ox] = y+oy<mapheight && x+ox<mapwidth && x+ox>=0 && y+oy>=0? tilechars[map.tileids[y+oy][x+ox]] : ' ';
            }
        }
        return new RenderedMap(ret);
    }
    public static Tilemap getTilemap(String name){
        return tilemaps.get(name);
    }
    public static void activate(){
        active = true;
    }
    public static void deactivate(){
        active = false;
    }
}
