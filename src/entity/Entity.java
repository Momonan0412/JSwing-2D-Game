package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage[] up, down, left, right, idle;
    public int upSize, downSize, leftSize, rightSize, idleSize;
    public String direction;
    public int spriteCounter = 0;
    public int transition = 0;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public String getDirection(){
        return direction;
    };
}
