public class RenderedMap {
    char[][] map;
    public RenderedMap(char[][] map){
        this.map = map;
    }
    public RenderedMap(int width, int height){
        map = new char[width][height];
    }
    public RenderedMap(int width, int height, char c){
        map = new char[height][width];
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                map[x][y] = c;
            }
        }
    }
    public void overlay(RenderedMap overlay){
        if(overlay.map.length != map.length || overlay.map[0].length != map[0].length){
            throw new IllegalArgumentException("Overlay must be the same size as the map. If you want to place it in a specific location, use the other overlay method.");
        }
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                if(overlay.map[x][y] != 0){
                    map[x][y] = overlay.map[x][y];
                }
            }
        }
    }
    public void overlay(RenderedMap overlay, int x, int y){
        if(overlay.map.length + y > map.length || overlay.map[0].length + x > map[0].length){
            throw new IllegalArgumentException("Overlay must fit within the map.");
        }
        for(int oy = 0; oy < overlay.map.length; oy++){
            for(int ox = 0; ox < overlay.map[0].length; ox++){
                if(overlay.map[oy][ox] != 0){
                    map[y+oy][x+ox] = overlay.map[oy][ox];
                }
            }
        }
    }
    @Override
    public String toString(){
        String s = "";
        for(char[] row : map){
            for(char c:row){
                s += c;
                s += c;
            }
            s += "\n";
        }
        return s;
    }
    public char get(int x, int y){
        return map[x][y];
    }
}
