package entity;

import interfaces.CloneableImageObject;
import interfaces.VisibilityCheck;
import main.GamePanel;
import main.UtilityTool;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.GamePanel.getGPTile;
/** ALL NPC WILL NOT HAVE THE "48 * 48" SIZE THEY ARE NOT THE SAME AS PER USUAL
 * SO, IT WILL NOT BE SCALED SIZE IS AS IT IS.
 * **/
public abstract class NonPlayableCharacter extends Entity implements VisibilityCheck, CloneableImageObject {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
//    private static final int DEFAULT_SOLID_AREA_WIDTH = 48;
//    private static final int DEFAULT_SOLID_AREA_HEIGHT = 48;
    public BufferedImage[] images; // Modified to use an array of images
    public String name;
    public boolean collision = false;
/**
 * Date 01/01/2024 COMMENTED OUT MAKES A BUG
 **/
//    public int worldX, worldY;
/**
 *  worldX and worldY already in the entity classs and should not be in "this class"
 **/
//    public Rectangle solidArea = new Rectangle(0,0,DEFAULT_SOLID_AREA_WIDTH,DEFAULT_SOLID_AREA_HEIGHT);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    UtilityTool utilityTool = new UtilityTool();

    public NonPlayableCharacter(GamePanel gp) {
        super(gp);
    }
    public void draw(Graphics2D g2) {
        BufferedImage[] image = null;
        try {
            if (gp == null) {
                throw new NullPointerException();
            } else {
                int screenX = super.worldX - gp.player.worldX + gp.player.screenX;
                int screenY = super.worldY - gp.player.worldY + gp.player.screenY;
                System.out.println("Direction: " + direction);
                System.out.println("World X: " + super.worldX);
                System.out.println("World Y: " + super.worldY);
                System.out.println("Game Panel World X: " + screenX);
                System.out.println("Game Panel World Y: " + screenY);
                if (isVisible(super.worldX, super.worldY, gp)) {
                    switch (super.direction) {
                        case "up":
                            image = super.up;
                            break;
                        case "down":
                            image = super.down;
                            break;
                        case "left":
                            image = super.left;
                            break;
                        case "right":
                            image = super.right;
                            break;
                        case "idle":
                            image = super.idle;
                            break;
                    }
                    if (image != null && spriteCounter < image.length) {
                        setImageAndRect(g2, screenX, screenY, spriteCounter);
//                        g2.drawImage(getImages()[spriteCounter], screenX, screenY, null);
//                        g2.setColor(Color.RED);
//                        g2.drawRect(screenX, screenY, 72, 105); /** Collision Check! Debug! **/
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract void setImageAndRect(Graphics2D g2, int screenX, int screenY, int spriteCounter);
    public void update() {
        setAction();
        collisionOn = false;

        /** "IF" TO BE DELETED!
         *  FOR DEBUGGING PURPOSES
         *  01/01/2024
         * **/
        if (gp.cChecker != null) {
            /** WILL BE AND SHOULD BE REVISED SINCE I FEEL LIKE THIS IS CHEATING! **/
            gp.cChecker.checkTile(this); // Check if this line is problematic
            /** NOT THE ISSUE, EVEN THOUGH IT POINTS IN HERE? **/
        }
        if(!collisionOn){
            switch (direction) {
                case "up":
//                    super.worldY -= speed; /** Previous used "wordX and worldY coordinates, which I think is wrong (?) **/
//                    gp.player.worldY -= speed; // 01/01/2024 // produces Earthquake Effect HAHAHAHA
                    super.worldY -= speed;
                    break;
                case "down":
//                    super.worldY += speed; /** Previous used "wordX and worldY coordinates, which I think is wrong (?) **/
//                    gp.player.worldY += speed; // 01/01/2024 // produces Earthquake Effect HAHAHAHA
                    super.worldY += speed;
                    break;
                case "left":
//                    super.worldX -= speed; /** Previous used "wordX and worldY coordinates, which I think is wrong (?) **/
//                    gp.player.worldX -= speed; // 01/01/2024 // produces Earthquake Effect HAHAHAHA
                    super.worldX -= speed;
                    break;
                case "right":
//                    super.worldX += speed; /** Previous used "wordX and worldY coordinates, which I think is wrong (?) **/
//                    gp.player.worldX += speed; // 01/01/2024 // produces Earthquake Effect HAHAHAHA
                    super.worldX += speed;
                    break;
            }
        }
        super.transition++;
        if(super.transition > 6){
            switch (super.direction) {
                case "up" -> super.spriteCounter = (super.spriteCounter + 1) % super.upSize;
                case "down" -> super.spriteCounter = (super.spriteCounter + 1) % super.downSize;
                case "left" -> super.spriteCounter = (super.spriteCounter + 1) % super.leftSize;
                case "right" -> super.spriteCounter = (super.spriteCounter + 1) % super.rightSize;
                case "idle" -> super.spriteCounter = (super.spriteCounter + 1) % super.idleSize;
            }
            super.transition = 0;
        }
    }
    public void setAction(){}

    public abstract BufferedImage[] getImages(); // Getter for the array of images
    @Override
    public CloneableImageObject cloneObject() {
        try {
            SuperObject clonedObject = (SuperObject) super.clone();
            clonedObject.images = new BufferedImage[images.length];

            for (int i = 0; i < images.length; i++) {
                BufferedImage originalImage = images[i];
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();

                clonedObject.images[i] = new BufferedImage(width, height, originalImage.getType());
                clonedObject.images[i].getGraphics().drawImage(originalImage.getSubimage(0, 0, width, height), 0, 0, null);
            }

            return clonedObject;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

}
