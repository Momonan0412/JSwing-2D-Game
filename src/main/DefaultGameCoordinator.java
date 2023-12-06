package main;

import interfaces.GameCoordinator;

public class DefaultGameCoordinator implements GameCoordinator {
    private static final Object globalLock = new Object();
    GamePanel gp;
    public DefaultGameCoordinator(GamePanel gp){
        this.gp = gp;
    }
    public void pauseAllThreads() {
        synchronized (globalLock) {
            if (gp.objectDrawerThread.isRunning()) {
                gp.objectDrawerThread.stopRunning();
            }
            if (gp.uiDrawerThread.isRunning()) {
                gp.uiDrawerThread.stopRunning();
            }
            if (gp.tileDrawerThread.isRunning()) {
                gp.tileDrawerThread.stopRunning();
            }
            if (gp.npcDrawerThread.isRunning()) {
                gp.npcDrawerThread.stopRunning();
            }
        }
    }
    public void resumeAllThreads() {
        synchronized (globalLock) {
            if (!gp.objectDrawerThread.isRunning()) {
                gp.objectDrawerThread.startRunning();
            }
            if (!gp.uiDrawerThread.isRunning()) {
                gp.uiDrawerThread.startRunning();
            }
            if (!gp.tileDrawerThread.isRunning()) {
                gp.tileDrawerThread.startRunning();
            }
            if (!gp.npcDrawerThread.isRunning()) {
                gp.npcDrawerThread.startRunning();
            }
            // Add logic for other threads if needed
        }
    }
}
