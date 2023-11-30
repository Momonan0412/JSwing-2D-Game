package main;

import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class ObjectDrawerThread extends Thread {
    private static final int UPDATE_INTERVAL_MS = 16;
    private volatile boolean isRunning = true;
    private GamePanel gamePanel;
    public SuperObject[] obj;
    public ObjectDrawerThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        obj = new SuperObject[10];
    }

    @Override
    public void run() {
        while (isRunning) {
            // Schedule a task to repaint Swing components on the EDT
            if(gamePanel.gameState == gamePanel.playState){
                SwingUtilities.invokeLater(() -> {
                    gamePanel.repaint();
                    updateAnimations();
                });
            }

            // Sleep for a short duration to control the update rate
            try {
                Thread.sleep(UPDATE_INTERVAL_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopRunning() { //PAUSE
        isRunning = false;
    }
    public void startRunning() { //PLAY
        isRunning = true;
    }
    public boolean isRunning() {
        return isRunning;
    }
    private void updateAnimations() {
        for (SuperObject o : obj) {
            if (o != null) {
                o.updateAnimation();
            }
        }
    }
    void drawObjects(Graphics2D g2) {
        // ... your drawing logic ...
        for (SuperObject o : obj){ ///////// 25/11/2023 Started to make it in a separate thread
            if(o != null){ // To avoid null pointer Error
                o.draw(g2, gamePanel); // For drawing the key
            }
        }
    }
    /**
     * Gets the SuperObject at the specified index.
     *
     * @param index the index of the SuperObject to retrieve
     * @return the SuperObject at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public SuperObject getObject(int index) {
        if (index >= 0 && index < obj.length) {
            return obj[index];
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
    /**
     * Sets the SuperObject at the specified index.
     *
     * @param index   the index at which to set the SuperObject
     * @param sObject the SuperObject to set
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void setObject(int index, SuperObject sObject) {
        if (index >= 0 && index < obj.length) {
            obj[index] = sObject;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
}
