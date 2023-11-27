package tile;

import interfaces.VisibilityCheck;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager implements VisibilityCheck {
    GamePanel gp;
    public Tile[] tile;

    UtilityTool uTool;
    public int mapTileNum[][];
    public TileManager(GamePanel gp){
        uTool = new UtilityTool();
        this.gp = gp;
        tile = new Tile[25];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/Maps/map001.txt");
    }
    public void getTileImage(){
            setUp(0,"Grass", false);
            setUp(1,"Small-Tree-Grass", true);
            setUp(2,"Grass-Rock", true);
            setUp(3,"Grass-Log", true);
            setUp(4,"Mizu1-Top", true);
            setUp(5,"Mizu2-Top", true);
            setUp(6,"Mizu3-Top", true);
            setUp(7,"Mizu1-Middle", true);
            setUp(8,"Mizu2-Middle", true);
            setUp(9,"Mizu3-Middle", true);
            setUp(10,"Mizu1-Bottom", true);
            setUp(11,"Mizu2-Bottom", true);
            setUp(12,"Mizu3-Bottom", true);
            setUp(14,"Grass-Stone", true);
            setUp(15,"Grass-Dirt", false);
            setUp(16,"Grass-Dirt2", false);
            setUp(17,"Sand1", false);
            setUp(18,"Sand2", true);
            setUp(19,"Sand3", false);
            setUp(20,"Sand4", false);
            setUp(21,"Sand5", true);
            setUp(22,"Sand6", false);
        try{
            /** TO-BE-REVISED
             *  IDEA: WHAT IF THIS WOULD BE A "SUPER-OBJECT"? OR MAYBE CREATE A DESIGN CLASS?
             *  OR JUST COMPLETE DELETE
             *  OR JUST MAKE IT AN ANIMATION
             * **/
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Aesthetic/Tree-Water.png")));/**(48*3)(48*3) PIXELS **/
            tile[13].image = uTool.scaleImage(tile[13].image, gp.tileSize*3, gp.tileSize*3);
            tile[13].collision = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0, row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line  = br.readLine();
                while (col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * * Wala ko kasabuut!
     * * Hays!
     * * https://youtu.be/Ny_YHoTYcxo?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
    **/
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0; /** WALA KO KASABUT! **/

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){ /** WALA KO KASABUT! **/

            int tileNum = mapTileNum[worldCol][worldRow]; /** WALA KO KASABUT! **/

            int worldX = worldCol * gp.tileSize; /** WALA KO KASABUT! **/
            int worldY = worldRow * gp.tileSize; /** WALA KO KASABUT! **/

            int screenX = worldX - gp.player.worldX + gp.player.screenX; /** WALA KO KASABUT! **/
            int screenY = worldY - gp.player.worldY + gp.player.screenY; /** WALA KO KASABUT! **/
            /**
             * This type of check is commonly used in game development to determine whether an object or
             * point is within the visible area of the game screen before rendering or processing it.
             * **/

            if (isVisible(worldX, worldY, gp)) { /** COMMON FOR GAME DEVELOPMENT**/
                if (tileNum != -1) {
                    /**
                     * 27/11/2023 : THIS WAS NOT NEEDED ANYMORE SINCE THE SCALE-IMAGE WAS INTRODUCED
                    int drawSize = (tileNum == 13) ? gp.tileSize * 3 : gp.tileSize;
                   g2.drawImage(tile[tileNum].image, screenX, screenY, drawSize, drawSize, null);
                   g2.drawRect(screenX, screenY, drawSize, drawSize); // Collision Check! Debug!
                 **/
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }


            worldCol++; /** WALA KO KASABUT! **/

            if(worldCol == gp.maxWorldCol){ /** WALA KO KASABUT! **/
                worldCol = 0;

                worldRow++; /** WALA KO KASABUT! **/

            }
        }
    }
    public void setUp(int index, String imagePath, boolean collision){
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Tiles/" + imagePath + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
