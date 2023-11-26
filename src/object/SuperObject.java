package object;

import entity.Entity;
import interfaces.CloneableImageObject;
import interfaces.VisibilityCheck;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject extends Entity implements VisibilityCheck, CloneableImageObject {
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

    @Override
    public void draw(Graphics2D g2) {
        try {
            draw(g2, null);
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // or log the exception
        }
    }
    public void draw(Graphics2D g2, GamePanel gp) {
//        System.out.println("Drawing frame: " + currentFrame);
        try {
            if (gp == null) {
                // Handle the case when no GamePanel is provided
            } else {
                // Existing logic that uses gp
//                System.out.println("Drawing frame: " + currentFrame);
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (isVisible(worldX, worldY, gp)) {
                    // Draw the current frame
                    g2.drawImage(images[currentFrame], screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // or log the exception
        }
    }
    public void updateAnimation() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime > FRAME_DELAY_MILLIS) {
            currentFrame = (currentFrame + 1) % images.length;
            lastFrameTime = currentTime;
        }
    }
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
