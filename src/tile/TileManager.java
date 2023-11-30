package tile;

import interfaces.VisibilityCheck;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.GamePanel.getGPTile;

public class TileManager implements VisibilityCheck {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    GamePanel gp;
    public Tile[] tile;

    UtilityTool uTool;
    public int mapTileNum[][];
    BufferedImage[] imgTemp;
    public TileManager(GamePanel gp){
        uTool = new UtilityTool();
        this.gp = gp;
        tile = new Tile[25];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/Maps/map001.txt");
    }
    public void getTileImage(){
            setUp(0,"Grass", false, 10);
            setUp(1,"Small-Tree-Grass", true, 1);
            setUp(2,"Grass-Rock", true, 1);
            setUp(3,"Grass-Log", true, 1);
            setUp(4,"Mizu1-Top", true, 1);
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * * Wala ko kasabuut!
     * * Hays!
     * * https://youtu.be/Ny_YHoTYcxo?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
    **/

    /** FOR THE CODE TO BE MORE READABLE!
     *  TO DO: DIVIDE THE DRAW METHOD INTO SECTION OF METHODS
     * **/
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
                    imgTemp = tile[tileNum].image;
                    g2.drawImage(tile[tileNum].image[currentFrame], screenX, screenY, null);
                }
            }


            worldCol++; /** WALA KO KASABUT! **/

            if(worldCol == gp.maxWorldCol){ /** WALA KO KASABUT! **/
                worldCol = 0;

                worldRow++; /** WALA KO KASABUT! **/

            }
        }
    }
    public void updateAnimation() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime > FRAME_DELAY_MILLIS) {
            currentFrame = (currentFrame + 1) % imgTemp.length;
            lastFrameTime = currentTime;
        }
    }
    public void setUp(int index, String imagePath, boolean collision, int numberImgs){
        try {
            tile[index] = new Tile(); /** Redundant? - To be tested *COMMENT THIS LINE OF CODE IF TILE DOES NOT WORK* **/
            tile[index] = tile[index].imageSetter(numberImgs);
            for(int i = 0; i < tile[index].image.length; i++){
                tile[index].image[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Tiles/" + imagePath + ( i+1 ) + ".png")));
                tile[index].image[i] = uTool.scaleImage(tile[index].image[i], getGPTile(), getGPTile());
            }
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
