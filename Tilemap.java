
public class Tilemap {
    public final int[][] tileids;
    public final int[][] tiledata;
    public final String theme;
    public Tilemap(int[][] tileids, int[][] tiledata){
        this.tileids = tileids;
        this.tiledata = tiledata;
        this.theme="Waterfall";
    }
    public Tilemap(int width, int height){
        tileids = new int[height][width];
        tiledata = new int[height][width];
        this.theme="Waterfall";
    }
    public Tilemap(int width, int height, int id, int data){
        tileids = new int[height][width];
        tiledata = new int[height][width];
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                tileids[x][y] = id;
                tiledata[x][y] = data;
            }
        }
        this.theme="Waterfall";
    }
    public Tilemap(int[][] tileids, int[][] tiledata, String theme){
        this.tileids = tileids;
        this.tiledata = tiledata;
        this.theme = theme;
    }
    public Tilemap(int width, int height, String theme){
        tileids = new int[height][width];
        tiledata = new int[height][width];
        this.theme=theme;
    }
    public Tilemap(int width, int height, int id, int data, String theme){
        tileids = new int[height][width];
        tiledata = new int[height][width];
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                tileids[x][y] = id;
                tiledata[x][y] = data;
            }
        }
        this.theme=theme;
    }
    public boolean checkBit(int x, int y, int digit){
        return (tiledata[x][y] & (1 << digit)) != 0;
    }
    public boolean isWalkable(int x, int y){
        return checkBit(x, y, 0);
    }
    public boolean isInteractable(int x, int y){
        return checkBit(x, y, 1);
    }
    public int[][] getAroundType(int x, int y){
        int[][] ret = new int[3][3];
        for(int ox = -1; ox < 2; ox++){
            for(int oy = -1; oy < 2; oy++){
                if(x+ox >= 0 && x+ox < tileids.length && y+oy >= 0 && y+oy < tileids[0].length){
                    ret[ox+1][oy+1] = tileids[x+ox][y+oy]!=0?(tileids[x+ox][y+oy]-1)%2+1:0;
                }else{
                    ret[ox+1][oy+1] = 0;
                }
            }
        }
        return ret;
    }
}
