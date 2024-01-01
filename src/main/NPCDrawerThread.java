package main;

import entity.Entity;
import entity.NonPlayableCharacter;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class NPCDrawerThread extends Thread {
    // Update interval for the thread in milliseconds
    private static final int UPDATE_INTERVAL_MS = 16;

    // Object used for synchronization
    private final Object pauseLock = new Object();

    // Volatile flag indicating whether the thread is running
    private volatile boolean isRunning = true; // Thread-safe flag for running status

    // Reference to the GamePanel
    private final GamePanel gamePanel;

    // Array to store NPC entities
    public NonPlayableCharacter[] NPC;

    // Constructor to initialize the thread with a GamePanel
    public NPCDrawerThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        NPC = new NonPlayableCharacter[10];
    }

    @Override
    public void run() {
        // The while loop checks the volatile isRunning flag, which ensures visibility across threads
        while (isRunning) {
            // Schedule a task to repaint Swing components on the EDT
            if (gamePanel.gameState == gamePanel.playState) {
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
            // Synchronize access to isRunning using the pauseLock object
            synchronized (pauseLock) {
                // While loop ensures proper handling of the isRunning flag changes by other threads
                while (!isRunning) {
                    try {
                        // Release the lock and wait until notified when isRunning changes
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // Stop the thread by setting isRunning to false
    public void stopRunning() {
        synchronized (pauseLock) {
            isRunning = false;
        }
    }
    // Start the thread by setting isRunning to true and notifying all waiting threads
    public void startRunning() {
        synchronized (pauseLock) {
            isRunning = true;
            pauseLock.notifyAll();
        }
    }

    // Check if the thread is currently running
    public boolean isRunning() {
        return isRunning;
    }

    // Update animations for all NPC entities
    private void updateAnimations() {
        for (NonPlayableCharacter o : NPC) {
            if (o != null) {
                o.update();
            }
        }
    }

    // Draw all NPC entities on the provided Graphics2D object
    void drawNPCs(Graphics2D g2) {
        for (NonPlayableCharacter o : NPC) {
            if(o != null){
                o.draw(g2);
            }
        }
    }

    // Get the NPC entity at the specified index
    public Entity getObject(int index) {
        if (index >= 0 && index < NPC.length) {
            return NPC[index];
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    // Set the NPC entity at the specified index
    public void setObject(int index, NonPlayableCharacter npc) {
        if (index >= 0 && index < NPC.length) {
            NPC[index] = npc;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
}
