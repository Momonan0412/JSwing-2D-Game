package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_MadameChiffon extends NonPlayableCharacter{
    public NPC_MadameChiffon(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle();
        solidArea.x = 12; // Adjusted x-coordinate for a 48x48 solid area within a 72x105 character
        solidArea.y = 30; // Adjusted y-coordinate for a 48x48 solid area within a 72x105 character
        super.solidAreaDefaultX = solidArea.x;
        super.solidAreaDefaultY = solidArea.y;
        solidArea.width = 48; // Width of the solid area
        solidArea.height = 48; // Height of the solid area


        setDefaultValues();
        getImage();
    }

    @Override
    public BufferedImage[] getImages() {
        return super.images;
    }
    public void setAction(){
        Random rand = new Random();
        int upperBound = 100;
        int numDirection = rand.nextInt(upperBound) + 1;

        if (numDirection <= 25) { // 25 Below
            super.direction = "left";
            super.images = super.left;
        } else if (numDirection <= 50) { // 26 to 50
            super.direction = "right";
            super.images = super.right;
        } else if (numDirection <= 75) { // 51 to 75
            super.direction = "up";
            super.images = super.up;
        } else { // 76 to 100
            super.direction = "down";
            super.images = super.down;
        }
    }

    public void setDefaultValues(){
        super.name = "Madame Chiffon";
        super.direction = "idle";
        super.rightSize = 8;
        super.downSize = 8;
        super.leftSize = 8;
        super.upSize = 8;
        super.right = new BufferedImage[rightSize];
        super.down = new BufferedImage[downSize];
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
        for(int i = 0; i < downSize; i++){
            super.down[i] = utilityTool.setup("NPC/MadameChiffonIdle1/", i, false);
        }
        for(int i = 0; i < upSize; i++){
            super.up[i] = utilityTool.setup("NPC/MadameChiffonIdle2/", i, false);
        }
    }
}