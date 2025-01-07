
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Tilemapmgr {
    public static final int TILESIZE = 20;
    static HashMap<String,Tilemap> tilemaps = new HashMap<>();
    public static final char[] tilechars = {' ','.','~','-','|','/'};
    static HashMap<String, HashMap<String,BufferedImage>> themes=new HashMap<>();
    static boolean active = false;
    static int offsetxpx = 0, offsetypx = 0;
    static Position position = new Position();
    static Tilemap current;
    static HashMap<String, BufferedImage> renderedMaps = new HashMap<>();
    public static void setmap(String name){
        current = tilemaps.get(name);
    }
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
            }
            if(dis.available()!=0){
                System.err.println("Possibly corrupt map file: "+name);
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
    public static RenderedCMap drawcmap(String name){
        char[][] ret = new char[tilemaps.get(name).tileids.length][tilemaps.get(name).tileids[0].length];
        Tilemap map = tilemaps.get(name);
        for(int x=0;x<map.tileids.length;x++){
            for(int y=0;y<map.tileids[0].length;y++){
                ret[x][y] = tilechars[map.tileids[x][y]];
            }
        }
        return new RenderedCMap(ret);
    }
    public static RenderedCMap drawcmap(int width, int height){
        int mapwidth = current.tileids.length, mapheight = current.tileids[0].length;
        char[][] ret = new char[height][width];
        Tilemap map = current;
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                ret[y][x] = y<mapheight && x<mapwidth && x>=0 && y>=0? tilechars[map.tileids[y][x]] : ' ';
            }
        }
        return new RenderedCMap(ret);
    }
    public static RenderedCMap drawcmap(String name, int x, int y, int width, int height){
        char[][] ret = new char[height][width];
        int mapwidth = tilemaps.get(name).tileids.length, mapheight = tilemaps.get(name).tileids[0].length;
        Tilemap map = tilemaps.get(name);
        for(int ox=0;ox<width;ox++){
            for(int oy=0;oy<height;oy++){
                ret[oy][ox] = y+oy<mapheight && x+ox<mapwidth && x+ox>=0 && y+oy>=0? tilechars[map.tileids[y+oy][x+ox]] : ' ';
            }
        }
        return new RenderedCMap(ret);
    }
    public static BufferedImage drawgmap(int width, int height) {
        if(!themes.containsKey(current.theme)){
            loadTheme(current.theme);
        }
        HashMap<String, BufferedImage> theme = themes.get(current.theme);
        BufferedImage tmp = new BufferedImage(width* TILESIZE, height*TILESIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.dispose();
        return tmp.getSubimage(offsetxpx, offsetypx, width*TILESIZE, height*TILESIZE);
    }
    public static BufferedImage drawmap(){
        if(!themes.containsKey(current.theme)){
            loadTheme(current.theme);
        }
        BufferedImage ret = new BufferedImage(current.tileids.length*TILESIZE, current.tileids[0].length*TILESIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = ret.getGraphics();
        for(int i=0;i<current.tileids.length;i++){
            for(int j=0;j<current.tileids[0].length;j++){
                g.drawImage(getTile(i, j), i*TILESIZE, j*TILESIZE, null);
            }
        }
        g.dispose();
        return ret;
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
    public static void loadTheme(String theme){
        System.out.println("Loading theme: "+theme);
        try{
            File folder = new File("./Tiles/"+theme);
            ArrayList<File> files;
            files = new ArrayList<>();
            for (String fileName : folder.list()) {
                files.add(new File(folder, fileName));
            }
            HashMap<String, BufferedImage> images = new HashMap<>();
            ArrayList<File> filesToAdd = new ArrayList<>();
            for(File i : files){
                if(i.isDirectory()){
                    System.out.println("Loading directory: "+i.getName());
                    filesToAdd.addAll(Arrays.asList(i.listFiles()));
                    // Recursive call to handle subdirectories
                    for(File subFile : i.listFiles()){
                        if(subFile.isDirectory()){
                            System.out.println("Loading directory: "+subFile.getName());
                            filesToAdd.addAll(Arrays.asList(subFile.listFiles()));
                        }else if(subFile.isFile()){
                            if(!subFile.getName().matches("^tile[0-9].*")) {
                                System.out.println("Loading: "+subFile.getName());
                                images.put(subFile.getName(), ImageIO.read(subFile));
                            }
                        }
                    }
                }else if(i.isFile()){
                    if(!i.getName().matches("^tile[0-9].*")) {
                        System.out.println("Loading: "+i.getName());
                        images.put(i.getName(), ImageIO.read(i));
                    }
                }
            }
            files.addAll(filesToAdd);
            System.out.println(files);
            for(File i : files){
                if(i.isFile()){
                    if(!i.getName().matches("^tile[0-9].*")) {
                        System.out.println("Loading: "+i.getName());
                        images.put(i.getName(), ImageIO.read(i));
                    }
                }
            }
            themes.put(theme, images);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static BufferedImage getTile(int x, int y){
        if(!themes.containsKey(current.theme)){
            loadTheme(current.theme);
        }
        HashMap<String, BufferedImage> theme = themes.get(current.theme);
        switch(current.tileids[x][y]){
            case 1 -> {
                int[][]around=current.getAroundType(x, y);
                if(around[0][1]==1){
                    if(around[1][0]==1){
                        if(around[2][1]==1){
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_center.png");
                            }else{
                                return (BufferedImage)theme.get("light_gnd_bottom.png");
                            }
                        }else{
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_right.png");
                            }else{
                                return (BufferedImage)theme.get("light_gnd_bottom_right.png");
                            }
                        }
                    }else{
                        if(around[2][1]==1){
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_top.png");
                            }
                        }else{
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_top_right.png");
                            }else{
                                return (BufferedImage)theme.get("light_gnd_top_right_bottom.png");
                            }
                        }
                    }
                }else{
                    if(around[1][0]==1){
                        if(around[2][1]==1){
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_left.png");
                            }else{
                                return (BufferedImage)theme.get("light_gnd_bottom_left.png");
                            }
                        }
                    }else{
                        if(around[2][1]==1){
                            if(around[1][2]==1){
                                return (BufferedImage)theme.get("light_gnd_top_left.png");
                            }
                        }
                    }
                }
            }
            case 0 ->{
                System.out.println("0");
                BufferedImage blackBox = new BufferedImage(TILESIZE, TILESIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics g = blackBox.getGraphics();
                g.setColor(java.awt.Color.BLACK);
                g.fillRect(0, 0, TILESIZE, TILESIZE);
                g.dispose();
                return blackBox;
            }
        }
        BufferedImage blackBox = new BufferedImage(TILESIZE, TILESIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackBox.getGraphics();
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, TILESIZE, TILESIZE);
        g.dispose();
        return blackBox;
    }
}
