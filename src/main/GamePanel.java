package main;

import entity.Player;
import tile.TileManager;
import object.SuperObject;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable { /** Need Runnable Interface To Use Thread **/
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // WORLD SETTINGS!
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 72;
    int FPS = 60;
    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound BGM = new Sound();
    Sound soundEffects = new Sound();
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public ObjectDrawerThread objectDrawerThread;
    public UIDrawerThread uiDrawerThread;
    Thread gameThread;
    // ENTITY AND OBJECT

    public Player player = new Player(this, keyH);
//    public SuperObject obj[] = new SuperObject[10];
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
    }
    public void setupGame(){
        objectDrawerThread.start();
        aSetter.setObject();
        uiDrawerThread.start();
        System.out.println("UI Drawer Thread!");
        playMusic(0);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2); // Layer 1 - Draw Tiles first then
        objectDrawerThread.drawObjects(g2); /**25/11/2023 Started to make it in a separate thread**/
        player.draw(g2); // Layer 2 - Draw Player
        uiDrawerThread.drawObjects(g2);
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
}
