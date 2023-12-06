package main;

import entity.Entity;
import object.SuperObject;

import static main.GamePanel.getGPTile;

public class CollisionChecker {
    private GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
        
    }
    public void checkTile(Entity entity){

        /** "IF ELSE" TO BE DELETED!
         *  FOR DEBUGGING PURPOSES
         * **/
        System.out.println("Checking tile for entity...");
        if (entity != null && entity.solidArea != null) {
        //
        //
            int entityLeftWorldX = entity.worldX + entity.solidArea.x;
            int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
            int entityTopWorldY = entity.worldY + entity.solidArea.y;
            int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
            /** https://youtu.be/oPzPpUcDiYY?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&t=1114
             * Explanation
             *
             * **/
            int entityLeftCol = entityLeftWorldX / getGPTile();
            int entityRightCol = entityRightWorldX / getGPTile();
            int entityTopRow = entityTopWorldY / getGPTile();
            int entityBottomRow = entityBottomWorldY / getGPTile();
            int tileNum1 = 0, tileNum2 = 0;
            switch(entity.direction){
                case "up":
                        entityTopRow = (entityTopWorldY - entity.speed) / getGPTile();
                        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow]; /** Collision of the pixel's top left most **/
                        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow]; /** Collision of the pixel's top right most **/
                        break;
                case "down":
                        entityBottomRow = (entityBottomWorldY + entity.speed) / getGPTile();
                        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow]; /** Collision of the pixel's bottom left most **/
                        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow]; /** Collision of the pixel's bottom right most **/
                        break;
                case "left":
                        entityLeftCol = (entityLeftWorldX - entity.speed) / getGPTile();
                        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow]; /** Collision of the pixel's top left most **/
                        tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow]; /** Collision of the pixel's bottom left most **/
                        break;
                case "right":
                        entityRightCol = (entityRightWorldX + entity.speed) / getGPTile();
                        tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow]; /** Collision of the pixel's top right most **/
                        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow]; /** Collision of the pixel's bottom right most **/
                    break;
            }
            if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                entity.collisionOn = true;
            }

            /** "IF ELSE" TO BE DELETED!
             *  FOR DEBUGGING PURPOSES
             * **/
        } else {
            System.out.println("Warning: NonPlayableCharacter or solidArea is null.");
            ///

        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;
        for(int i = 0; i < gp.objectDrawerThread.obj.length; i++){
            if(gp.objectDrawerThread.obj[i] != null){
                /** Get entity's solid area position **/
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                /** Get the object's solid area position **/
                gp.objectDrawerThread.obj[i].solidArea.x = gp.objectDrawerThread.obj[i].worldX + gp.objectDrawerThread.obj[i].solidArea.x;
                gp.objectDrawerThread.obj[i].solidArea.y = gp.objectDrawerThread.obj[i].worldY + gp.objectDrawerThread.obj[i].solidArea.y;
                /**
                 * Reason for not using intersects: https://youtu.be/srvDSypsJL0?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&t=970
                 * **/
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.objectDrawerThread.obj[i].solidArea)){ /** Check if the two rectangle are colliding **/
                            System.out.println("Touch Up");
                            if(gp.objectDrawerThread.obj[i].collision) entity.collisionOn = true;
                            if(player) index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.objectDrawerThread.obj[i].solidArea)){ /** Check if the two rectangle are colliding **/
                            System.out.println("Touch Down");
                            if(gp.objectDrawerThread.obj[i].collision) entity.collisionOn = true;
                            if(player) index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.objectDrawerThread.obj[i].solidArea)){ /** Check if the two rectangle are colliding **/
                            System.out.println("Touch Left");
                            if(gp.objectDrawerThread.obj[i].collision) entity.collisionOn = true;
                            if(player) index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.objectDrawerThread.obj[i].solidArea)){ /** Check if the two rectangle are colliding **/
                            System.out.println("Touch Right");
                            if(gp.objectDrawerThread.obj[i].collision) entity.collisionOn = true;
                            if(player) index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX; // reset solid area to default
                entity.solidArea.y = entity.solidAreaDefaultY; // reset solid area to default
                gp.objectDrawerThread.obj[i].solidArea.x = gp.objectDrawerThread.obj[i].solidAreaDefaultX; // reset solid area to default
                gp.objectDrawerThread.obj[i].solidArea.y = gp.objectDrawerThread.obj[i].solidAreaDefaultY; // reset solid area to default
            }
        }
        return index;
    }
}
