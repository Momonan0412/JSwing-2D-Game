package main;


import javax.swing.*;
import java.awt.*;

public class UIDrawerThread extends Thread{
    private static final int UPDATE_INTERVAL_MS = 16;
    private final Object pauseLock = new Object();
    private volatile boolean isRunning = true;
    private final GamePanel gamePanel;

    public static UI[] uiDisplay;
    public UIDrawerThread(GamePanel gp){
        this.gamePanel = gp;
        uiDisplay = new UI[10];
        uiDisplay[0] = new UI(gp);
        for (int i = 1; i < uiDisplay.length; i++) {
            uiDisplay[i] = uiDisplay[0].clone();
        }
    }
    @Override
    public void run() {
        while (isRunning) {
            // Schedule a task to repaint Swing components on the EDT
//            System.out.println("UI Drawer Thread!");
            if(gamePanel.gameState == gamePanel.playState){
                SwingUtilities.invokeLater(() -> {
                    gamePanel.repaint();
                    updateAnimations();
                });
            }
            try {
                Thread.sleep(UPDATE_INTERVAL_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (pauseLock) {
                while (!isRunning) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void stopRunning() { // PAUSE
        synchronized (pauseLock) {
            isRunning = false;
        }
    }

    public void startRunning() { // RESUME
        synchronized (pauseLock) {
            isRunning = true;
            pauseLock.notifyAll();
        }
    }
    public boolean isRunning() {
        return isRunning;
    }
    private void updateAnimations() {
//        System.out.println("updateAnimations");
        for (UI u : uiDisplay) {
            if (u != null) {
//                System.out.println("Inside! updateAnimations");
//                u.updateAnimation();
            }
        }
    }
    void drawObjects(Graphics2D g2) {
//        System.out.println("DrawObjectMethod");
        // ... your drawing logic ...
        for (UI u : uiDisplay){ ///////// 25/11/2023 Started to make it in a separate thread
            if(u != null){ // To avoid null pointer Error
//                System.out.println("Inside! drawObjects");
//                u.draw(g2); // For drawing the key
            }
        }
    }
}
