package main;

import object.OBJ_Key;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI implements Cloneable {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private long messageStartTime = 0;
    private static final long MESSAGE_DURATION_MS = 5000;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    GamePanel gp;
    Font arial_40, arial_80B;
    OBJ_Key key;
    BufferedImage[] images;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    int counter = 0;
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        key = new OBJ_Key();
        images = key.images;
    }
    public void ShowMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(key.images[currentFrame], gp.tileSize/2,gp.tileSize/2, gp.tileSize,gp.tileSize, null);
    }
    public void writeMessage(Graphics2D g2){
        // Beside Key Animation
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if (gameFinished == true) {

            String text = "You found the treasure!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - (gp.tileSize * 3);

            g2.drawString(text, x, y);

            text = "Your time: " + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 5);

            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            text = "Congrats!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 3);

            g2.drawString(text, x, y);

            gp.gameThread = null;
        }else {

            g2.drawString("x " + gp.player.hasKey, 80, 60);
            playTime += (double) 1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11 , 60);
            // Picked Up "Message"
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                if (messageStartTime == 0) {
                    // Record the start time when the message is first shown
                    messageStartTime = System.currentTimeMillis();
                } else {
                    // Check if the message duration has elapsed
                    long elapsedTime = System.currentTimeMillis() - messageStartTime;
                    if (elapsedTime > MESSAGE_DURATION_MS) {
                        messageOn = false;
                        messageStartTime = 0; // Reset the start time
                    }
                }
            }
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
