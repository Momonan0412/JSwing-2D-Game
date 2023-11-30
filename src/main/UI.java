package main;

import object.OBJ_Key;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static main.GamePanel.getGPTile;

public class UI implements Cloneable {
    private static final int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }
    public void ShowMessage(String text){
        message = text;
        messageOn = true;
    }

    public void drawUI(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.playState){
            // ADD PLAY STATE STUFF
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredX(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }
    public int getXForCenteredX(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
//    public void updateAnimation() {
//        long currentTime = System.currentTimeMillis();
//
//        if (currentTime - lastFrameTime > FRAME_DELAY_MILLIS) {
//            currentFrame = (currentFrame + 1) % key.images.length;
//            lastFrameTime = currentTime;
//        }
//    }
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
