package tile;

import interfaces.GameCoordinator;
import main.GamePanel;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class TileDrawerThread extends Thread {
    private static final int UPDATE_INTERVAL_MS = 16;
    private volatile boolean isRunning = true;
    private final Object pauseLock = new Object();
    private final GamePanel gamePanel;
    public TileManager[] tileManager;
    TileManager tileGetter;
    public TileDrawerThread(GamePanel gamePanel, TileManager sharedTileM) {
        this.gamePanel = gamePanel;
        tileGetter = sharedTileM;
        tileManager = new TileManager[100];
        for (int i = 0; i < tileManager.length; i++) {
            tileManager[i] = new TileManager(gamePanel);
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            // Schedule a task to repaint Swing components on the EDT
            if(gamePanel.gameState == gamePanel.playState){
                SwingUtilities.invokeLater(() -> {
                    gamePanel.repaint();
                    updateDrawerThread();
                });
            }

            // Sleep for a short duration to control the update rate
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
    public void stopRunning() {
        synchronized (pauseLock) {
            isRunning = false;
        }
    }

    public void startRunning() {
        synchronized (pauseLock) {
            isRunning = true;
            pauseLock.notifyAll();
        }
    }
    public boolean isRunning() {
        return isRunning;
    }
    private void updateDrawerThread() {
        for (TileManager o : tileManager) {
            if (o != null) {
//                System.out.println("Debugging: updateAnimations Method");
                o.updateAnimation();
            }
        }
    }
    public void drawTiles(Graphics2D g2) {
        for (TileManager o : tileManager){
            if(o != null){
                o.draw(g2);
            }
        }
    }
    public void setTiles(int index, TileManager tileManager) {
        if (index >= 0 && index < this.tileManager.length) {
            this.tileManager[index] = tileManager;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
}
