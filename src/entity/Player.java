package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int hasKey = 0;
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.screenX = (gp.screenWidth/2) - (gp.tileSize/2);
        this.screenY = (gp.screenHeight/2) - (gp.tileSize/2);
//        solidArea = new Rectangle(5, 16, gp.tileSize-20, gp.tileSize-20); /** Adjust these values and check what works for the game **/
        solidArea = new Rectangle();
        solidArea.x = 13; /** x*2 + with == 48 **/
        solidArea.y = 30; /** y + height == 48 **/
        super.solidAreaDefaultX = solidArea.x;
        super.solidAreaDefaultY = solidArea.y;
        solidArea.width = 22; /** 28 + 10*2 = 48 **/
        solidArea.height = 18;
        setDefaultValues();
        getPlayerImage();
    }

    private static int frameSize(int size) {
        return size;
    }
    public void setDefaultValues(){
        super.worldX = gp.tileSize*36;
        super.worldY = gp.tileSize*32;
        super.speed = 2;
        super.upSize = 8; /** # Frames **/
        super.downSize = 8; /** # Frames **/
        super.leftSize = 8; /** # Frames **/
        super.rightSize = 8; /** # Frames **/
        super.idleSize = 3;
        super.direction = "idle"; /** Initial position shown or static **/
        up = new BufferedImage[upSize];
        down = new BufferedImage[downSize];
        left = new BufferedImage[leftSize];
        right = new BufferedImage[rightSize];
        idle = new BufferedImage[idleSize];
    }
    public void getPlayerImage(){
        try {
            for(int i = 0; i < upSize; i++){
                super.up[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Unfinished 2D Character/Back" + (i + 1) + ".png")));
            }
            for(int i = 0; i < downSize; i++){
                super.down[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Unfinished 2D Character/Front" + (i + 1) + ".png")));
            }
            for(int i = 0; i < leftSize; i++){
                super.left[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Unfinished 2D Character/Left" + (i + 1) + ".png")));
            }
            for(int i = 0; i < rightSize; i++){
                super.right[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Unfinished 2D Character/Right" + (i + 1) + ".png")));
            }
            for(int i = 0; i < idleSize; i++){
                super.idle[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Unfinished 2D Character/Idle" + (i + 1) + ".png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(){

        if(keyH.upPressed){
            super.direction = "up";
        } else if (keyH.downPressed) {
            super.direction = "down";
        } else if (keyH.leftPressed) {
            super.direction = "left";
        } else if (keyH.rightPressed) {
            super.direction = "right";
        }
//        else {
//            super.direction = "idle";
//        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            /**
             * CHECK TILE COLLISION
             * **/
            collisionOn = false;
            /**
             * CHECK OBJECT COLLISION
             * **/
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            gp.cChecker.checkTile(this);
            if(!collisionOn){
                switch (direction){
                    case "up": super.worldY -= speed; break;
                    case "down": super.worldY += speed; break;
                    case "left": super.worldX -= speed; break;
                    case "right": super.worldX += speed; break;
                }
            }
            super.transition++;
            if(super.transition > 6){
                switch (super.direction) {
                    case "up":
                        super.spriteCounter = (super.spriteCounter + 1) % super.upSize;
                        break;
                    case "down":
                        super.spriteCounter = (super.spriteCounter + 1) % super.downSize;
                        break;
                    case "left":
                        super.spriteCounter = (super.spriteCounter + 1) % super.leftSize;
                        break;
                    case "right":
                        super.spriteCounter = (super.spriteCounter + 1) % super.rightSize;
                        break;
                    case "idle":
                        Random idle = new Random();
                        super.spriteCounter = idle.nextInt(100);
                        if(super.spriteCounter > 1){
                            super.spriteCounter = 2;
                        }
                }
                super.transition = 0;
            }
        }
    }
    public void pickUpObject(int index) {
        if (index != 999 && gp.objectDrawerThread.getObject(index) != null) {
            String objectName = gp.objectDrawerThread.obj[index].name;
            switch (objectName) {
                case "Key" -> {
                    // gp.playSE(/** NUMBER HERE IF THE SOUND EXIST! **/);
                    hasKey++;
                    gp.objectDrawerThread.setObject(index, null);
                    System.out.println("Key: " + hasKey);
                }
                case "Door" -> {
                    // gp.playSE(/** NUMBER HERE IF THE SOUND EXIST! **/);
                    if (hasKey > 0) {
                        gp.objectDrawerThread.setObject(index, null);
                        hasKey--;
                    }
                    System.out.println("Key: " + hasKey);
                }
                case "Orb" -> {
                    // gp.playSE(/** NUMBER HERE IF THE SOUND EXIST! **/);
                    speed += 2;
                    gp.objectDrawerThread.setObject(index, null);
                }
            }
        }
    }
    @Override
    public void draw(Graphics2D g2) {

            BufferedImage[] image = null;
            switch (super.direction) {
                case "up":
                    image = super.up;
                    break;
                case "down":
                    image = super.down;
                    break;
                case "left":
                    image = super.left;
                    break;
                case "right":
                    image = super.right;
                    break;
                case "idle":
                    image = super.idle;
                    break;
            }
            if (image != null && spriteCounter < image.length) {
                g2.drawImage(image[spriteCounter], screenX, screenY, gp.tileSize, gp.tileSize, null);
                g2.setColor(Color.RED); // or any other color
                g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize); /** Collision Check! Debug! **/
            }
    }
}