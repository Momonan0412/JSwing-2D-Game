package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.GamePanel.getGPTile;
/**
 * FIX THE IDLE STATE
 * IMPLEMENT A LOGIC FOR WHEN THE NPC IS OUTSIDE THE WORLD'S COORDINATE
 * MAKE A COLLISION LOGIC WHEN NPC BUMP INTO COLLISION == TRUE "actionLockCounter" = 120, WHICH IS THE "IF"'S CONDITION
 * **/
public class NPC_MadameChiffon extends NonPlayableCharacter{
    public NPC_MadameChiffon(GamePanel gp) {
        super(gp);
        solidArea = new Rectangle();
        solidArea.x = 24; // Adjusted x-coordinate for a 48x48 tile solid area within a 72x105 character
        solidArea.y = 70; // Adjusted y-coordinate for a 48x48 tile solid area within a 72x105 character
        super.solidAreaDefaultX = solidArea.x;
        super.solidAreaDefaultY = solidArea.y;
        solidArea.width = 24; // Width of the solid area
        solidArea.height = 35; // Height of the solid area


        setDefaultValues();
        getImage();
    }

    @Override
    public void setImageAndRect(Graphics2D g2, int sX, int sY, int spriteCtr) {
        g2.drawImage(getImages()[spriteCtr], sX, sY, null);
        g2.setColor(Color.RED);
        g2.drawRect(sX, sY, 72, 105);
        g2.setColor(Color.BLUE);
        g2.drawRect(sX + solidArea.x, sY  + solidArea.y, solidArea.width, solidArea.height);
    }

    @Override
    public BufferedImage[] getImages() throws NullPointerException {
        return super.images;
    }
    public void setAction(){
        Random rand = new Random();
        int upperBound = 100;
        int numDirection = rand.nextInt(upperBound) + 1;

        if (numDirection <= 20) { // 25 Below
            super.direction = "left";
            super.images = super.left;
        } else if (numDirection <= 40) { // 26 to 50
            super.direction = "right";
            super.images = super.right;
        } else if (numDirection <= 60) { // 51 to 75
            super.direction = "up";
            super.images = super.up;
        } else if(numDirection <= 80){ // 76 to 100
            super.direction = "down";
            super.images = super.down;
        } else {
//            super.direction = "idle";
//            /** MAKE AN IDLE FRAMES?! **/
//            super.images = super.down; /** USING THE "DOWN" AS A TEST **/
//            /** MAKE AN IDLE FRAMES?! **/
        }
    }

    public void setDefaultValues(){
        super.name = "Madame Chiffon";
        super.direction = "idle";
        super.rightSize = 8;

        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/
        /** 03/01/2024 **/
        super.downSize = 8;
//        super.idleSize = 8;
        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/
        /** 03/01/2024 **/

        super.leftSize = 8;
        super.upSize = 8;
        super.right = new BufferedImage[rightSize];

        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/
        /** 03/01/2024 **/
        super.down = new BufferedImage[downSize];
//        super.idle = new BufferedImage[downSize];
        /** 03/01/2024 **/
        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/

        super.left = new BufferedImage[leftSize];
        super.up = new BufferedImage[upSize];
        super.speed = 1;
    }
    public void getImage(){
        for(int i = 0; i < leftSize; i++){
            super.left[i] = utilityTool.setup("NPC/MadameChiffonWalk1/", i, false);
        }
        for(int i = 0; i < rightSize; i++){
            super.right[i] = utilityTool.setup("NPC/MadameChiffonWalk2/", i, false);
        }

        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/
        /** 03/01/2024 **/
        for(int i = 0; i < downSize; i++){
            super.down[i] = utilityTool.setup("NPC/MadameChiffonIdle1/", i, false);
        }
//        for(int i = 0; i < downSize; i++){
//            super.idle[i] = utilityTool.setup("NPC/MadameChiffonIdle1/", i, false);
//        }
        /** 03/01/2024 **/
        /** MADE THE DOWN FRAMES AS IDLE FRAMES SINCE IDLE FRAMES IS NOT YET MADE!**/

        for(int i = 0; i < upSize; i++){
            super.up[i] = utilityTool.setup("NPC/MadameChiffonIdle2/", i, false);
        }
    }
}
