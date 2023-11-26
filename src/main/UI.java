package main;

import object.OBJ_Key;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI implements Cloneable {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    GamePanel gp;
    Font arial_40;
    OBJ_Key key;
    BufferedImage[] images;
    public boolean messageOn = false;
    public String message = "";
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        key = new OBJ_Key();
        images = key.images;
    }
    public void ShowMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
//        System.out.println(gp.tileSize);
        g2.drawImage(key.images[currentFrame], gp.tileSize/2,gp.tileSize/2, gp.tileSize,gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 80, 60);
        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message,gp.tileSize/2, gp.tileSize*5);
            messageOn = false;
        }
    }
    public void updateAnimation() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime > FRAME_DELAY_MILLIS) {
            currentFrame = (currentFrame + 1) % key.images.length;
            lastFrameTime = currentTime;
        }
    }
    @Override
    public UI clone() {
        try {
            UI clonedUI = (UI) super.clone();

            // Perform deep copy for fields that reference mutable objects
            clonedUI.arial_40 = new Font(arial_40.getName(), arial_40.getStyle(), arial_40.getSize());
            // Perform deep copy for other mutable fields...

            return clonedUI;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported for UI class", e);
        }
    }
}
