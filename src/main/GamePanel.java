package main;

import entity.Player;
import tile.TileManager;
import object.SuperObject;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable { /** Need Runnable Interface To Use Thread **/
    // SCREEN SETTINGS
    static final int originalTileSize = 16; // 16x16 tile
    static final int scale = 3;
    public static int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // WORLD SETTINGS!
    public final int maxWorldCol = 64; // Change depends on map?
    public final int maxWorldRow = 72; // Change depends on map?
    int FramePerSecond = 60;
    // HANDLE THREADS?
    private final Object lockObject = new Object();
    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound BGM = new Sound();
    Sound soundEffects = new Sound();
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    // THREADS
    public ObjectDrawerThread objectDrawerThread;
    public UIDrawerThread uiDrawerThread;
    public UI showMessage;
    Thread gameThread;
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // Corrected colon to semicolon
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        cChecker = new CollisionChecker(this); /** COLLISION **/
        objectDrawerThread = new ObjectDrawerThread(this);
        uiDrawerThread = new UIDrawerThread(this);
        aSetter = new AssetSetter(this, objectDrawerThread);
        showMessage = new UI(this);
    }
    public void setupGame(){
        objectDrawerThread.start();
        aSetter.setObject();
        uiDrawerThread.start();
        System.out.println("UI Drawer Thread!");
        playMusic(0);
        stopMusic(); // MUTE GAME
        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FramePerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long playTime = 0; /** TO DO FIX TIMER **/
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                /** ISSUE: TIMER RESETS **/
                playTime += timer; /** TO DO FIX TIMER **/
                update();
                repaint();
                delta--;
                drawCount++;
                /** ISSUE: TIMER RESETS **/
                /** SHOULD I USE THE "showMessage" ?
                 *  OR INSTANIATE ANOTHER ?
                 * **/
//                showMessage.setPlayTime((double) playTime / 1000000000L); /** TO DO FIX TIMER  **/
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        synchronized (lockObject) {
            if (gameState == playState) {
                if (!objectDrawerThread.isRunning()) {
                    objectDrawerThread.startRunning();
                }
                if (!uiDrawerThread.isRunning()) {
                    uiDrawerThread.startRunning();
                }
                player.update();
            } else if (gameState == pauseState) {
                if (objectDrawerThread.isRunning()) {
                    objectDrawerThread.stopRunning();
                }
                if (uiDrawerThread.isRunning()) {
                    uiDrawerThread.stopRunning();
                }
            }
        }
    }


    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);

        /**Debugging**/
        long drawStart = 0;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }
        /**Debugging**/

        tileM.draw(g2); // Layer 1 - Draw Tiles first
        objectDrawerThread.drawObjects(g2); // Layer 2 - Draw Objects
        player.draw(g2); // Layer 3 - Draw Player

        // Draw UI elements and show messages
        uiDrawerThread.drawObjects(g2);
        showMessage.drawUI(g2);
        /**Debugging**/
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        /**Debugging**/

        g2.dispose();
    }
    public void playMusic(int i){
        BGM.setFile(i);
//        BGM.play();
        BGM.loop();
        BGM.setVolume(-10.0f);
    }
    public void stopMusic(){
        BGM.stop();
    }
    public void playSE(int i){
        soundEffects.setFile(i);
        soundEffects.play();
    }

    public static int getGPTile() {
        return tileSize;
    }
}
