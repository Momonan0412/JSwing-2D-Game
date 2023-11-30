package main;

import object.*;

import static main.GamePanel.getGPTile;

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

        drawerThread.obj[0].worldX = getGPTile();
        drawerThread.obj[0].worldY = getGPTile();

        drawerThread.obj[1].worldX = 60 * getGPTile();
        drawerThread.obj[1].worldY = 70 * getGPTile();

        drawerThread.obj[2].worldX = 30 * getGPTile();
        drawerThread.obj[2].worldY = 35 * getGPTile();

        drawerThread.obj[3] = new OBJ_Door();
        drawerThread.obj[4] = (OBJ_Door) drawerThread.obj[3].cloneObject();

        drawerThread.obj[3].worldX = getGPTile();
        drawerThread.obj[3].worldY = 70 * getGPTile();

        drawerThread.obj[4].worldX = 60 * getGPTile();
        drawerThread.obj[4].worldY = getGPTile();

        drawerThread.obj[5] = new OBJ_Chest();
        drawerThread.obj[5].worldX = 30 * getGPTile();
        drawerThread.obj[5].worldY = 70 * getGPTile();

        drawerThread.obj[6] = new OBJ_Orb();
        drawerThread.obj[6].worldX = 31 * getGPTile();
        drawerThread.obj[6].worldY = 36 * getGPTile();

        drawerThread.obj[7] = new OBJ_Tornado();
        drawerThread.obj[7].worldX = 19 * getGPTile();
        drawerThread.obj[7].worldY = 19 * getGPTile();
    }
}
