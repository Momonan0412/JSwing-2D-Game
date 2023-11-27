package main;

import object.*;

public class AssetSetter {
    public GamePanel gp;
    public ObjectDrawerThread drawerThread;
    public AssetSetter(GamePanel gp, ObjectDrawerThread drawerThread) {
        this.gp = gp;
        this.drawerThread = drawerThread;
    }
    public void setObject(){
        drawerThread.obj[0] = new OBJ_Key();
        drawerThread.obj[1] = (OBJ_Key)drawerThread.obj[0].cloneObject();
        drawerThread.obj[2] = (OBJ_Key)drawerThread.obj[0].cloneObject();

        drawerThread.obj[0].worldX = 23 * gp.tileSize;
        drawerThread.obj[0].worldY = 23 * gp.tileSize;

        drawerThread.obj[1].worldX = 25 * gp.tileSize;
        drawerThread.obj[1].worldY = 25 * gp.tileSize;

        drawerThread.obj[2].worldX = 27 * gp.tileSize;
        drawerThread.obj[2].worldY = 27 * gp.tileSize;

        drawerThread.obj[3] = new OBJ_Door();
        drawerThread.obj[4] = (OBJ_Door) drawerThread.obj[3].cloneObject();

        drawerThread.obj[3].worldX = 26 * gp.tileSize;
        drawerThread.obj[3].worldY = 26 * gp.tileSize;

        drawerThread.obj[4].worldX = 24 * gp.tileSize;
        drawerThread.obj[4].worldY = 24 * gp.tileSize;

        drawerThread.obj[5] = new OBJ_Chest();
        drawerThread.obj[5].worldX = 20 * gp.tileSize;
        drawerThread.obj[5].worldY = 20 * gp.tileSize;

        drawerThread.obj[6] = new OBJ_Orb();
        drawerThread.obj[6].worldX = 29 * gp.tileSize;
        drawerThread.obj[6].worldY = 29 * gp.tileSize;

        drawerThread.obj[7] = new OBJ_Tornado();
        drawerThread.obj[7].worldX = 30 * gp.tileSize;
        drawerThread.obj[7].worldY = 30 * gp.tileSize;
    }
}
