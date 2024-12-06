
public class Tilemap {
    public final int[][] tileids;
    public final int[][] tiledata;
    public Tilemap(int[][] tileids, int[][] tiledata){
        this.tileids = tileids;
        this.tiledata = tiledata;
    }
    public Tilemap(int width, int height){
        tileids = new int[height][width];
        tiledata = new int[height][width];
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
}
