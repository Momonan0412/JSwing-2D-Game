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

public abstract class NonPlayableCharacter extends Entity implements VisibilityCheck, CloneableImageObject {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private static final int DEFAULT_SOLID_AREA_WIDTH = 48;
    private static final int DEFAULT_SOLID_AREA_HEIGHT = 48;
    public BufferedImage[] images; // Modified to use an array of images
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,DEFAULT_SOLID_AREA_WIDTH,DEFAULT_SOLID_AREA_HEIGHT);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    UtilityTool utilityTool = new UtilityTool();

    public NonPlayableCharacter(GamePanel gp) {
        super(gp);
    }
    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage[] image = null;
        try {
            if (gp == null) {
                throw new NullPointerException();
            } else {
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (isVisible(worldX, worldY, gp)) {
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
                        g2.drawImage(getImages()[spriteCounter], screenX, screenY, null);
                        g2.setColor(Color.RED);
                        g2.drawRect(screenX, screenY, getGPTile(), getGPTile()); /** Collision Check! Debug! **/
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update() {
        setAction();
        collisionOn = false;

        /** "IF" TO BE DELETED!
         *  FOR DEBUGGING PURPOSES
         * **/
        if (gp.cChecker != null) {
            /** WILL BE AND SHOULD BE REVISED SINCE I FEEL LIKE THIS IS CHEATING! **/
            gp.cChecker.checkTile(this); // Check if this line is problematic
            /** NOT THE ISSUE, EVEN THOUGH IT POINTS IN HERE? **/
        }
        //
        if(!collisionOn){
            switch (direction) {
                case "up" -> super.worldY -= speed;
                case "down" -> super.worldY += speed;
                case "left" -> super.worldX -= speed;
                case "right" -> super.worldX += speed;
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
    public abstract void setAction();

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
