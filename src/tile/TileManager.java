package tile;

import interfaces.VisibilityCheck;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager implements VisibilityCheck {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[25];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/Maps/map001.txt");
    }
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Small-Tree-Grass.png"));
            tile[1].collision = true;
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass-Rock.png"));
            tile[2].collision = true;
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass-Log.png"));
            tile[3].collision = true;
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu1-Top.png"));
            tile[4].collision = true;
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu2-Top.png"));
            tile[5].collision = true;
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu3-Top.png"));
            tile[6].collision = true;
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu1-Middle.png"));
            tile[7].collision = true;
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu2-Middle.png"));
            tile[8].collision = true;
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu3-Middle.png"));
            tile[9].collision = true;
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu1-Bottom.png"));
            tile[10].collision = true;
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu2-Bottom.png"));
            tile[11].collision = true;
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Mizu3-Bottom.png"));
            tile[12].collision = true;
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/res/Aesthetic/Tree-Water.png")); /**(48*3)(48*3) PIXELS **/
            tile[13].collision = true;
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass-Stone.png"));
            tile[14].collision = true;
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass-Dirt.png"));
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Grass-Dirt2.png"));
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand1.png"));
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand2.png"));
            tile[18].collision = true;
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand3.png"));
            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand4.png"));
            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand5.png"));
            tile[21].collision = true;
            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/Sand6.png"));
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
                    int drawSize = (tileNum == 13) ? gp.tileSize * 3 : gp.tileSize;
                    g2.drawImage(tile[tileNum].image, screenX, screenY, drawSize, drawSize, null);
                    g2.drawRect(screenX, screenY, drawSize, drawSize); /** Collision Check! Debug! **/
                }
            }


            worldCol++; /** WALA KO KASABUT! **/

            if(worldCol == gp.maxWorldCol){ /** WALA KO KASABUT! **/
                worldCol = 0;

                worldRow++; /** WALA KO KASABUT! **/

            }
        }
    }
}
