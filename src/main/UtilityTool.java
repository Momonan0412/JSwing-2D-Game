package main;

import entity.NonPlayableCharacter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.getGPTile;

/**
 * A utility class for common image-related operations.
 */
public class UtilityTool {

    /**
     * Scales an image to the specified width and height using the provided BufferedImage.
     *
     * @param original The original image to be scaled.
     * @param width    The desired width of the scaled image.
     * @param height   The desired height of the scaled image.
     * @return A new BufferedImage representing the scaled image.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // Create a new BufferedImage with the specified width, height, and image type.
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());

        // Get the Graphics2D object of the scaled image for drawing operations.
        Graphics2D g2 = scaledImage.createGraphics();

        // Draw the original image onto the scaled image with the specified width and height.
        g2.drawImage(original, 0, 0, width, height, null);

        // Dispose of the Graphics2D object to release resources.
        g2.dispose();

        // Return the scaled image.
        return scaledImage;
    }

    public BufferedImage setup(String path, int index, boolean playable){
        BufferedImage img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/" + path + (index + 1) + ".png")));
            if(playable){
                img = scaleImage(img, getGPTile(), getGPTile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}

