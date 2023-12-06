package main;

import entity.NPC_MadameChiffon;
import object.*;

import static main.GamePanel.getGPTile;

public class AssetSetter {
    public GamePanel gp;
    public ObjectDrawerThread objDrawerThread;
    public NPCDrawerThread npcDrawerThread;
    public AssetSetter(GamePanel gp, ObjectDrawerThread objDrawerThread, NPCDrawerThread npcDrawerThread) {
        this.gp = gp;
        this.objDrawerThread = objDrawerThread;
        this.npcDrawerThread = npcDrawerThread;
    }
    public void setNpc(){
        npcDrawerThread.NPC[0] = new NPC_MadameChiffon(gp);
        npcDrawerThread.NPC[0].worldX = 31 * getGPTile();
        npcDrawerThread.NPC[0].worldY = 36 * getGPTile();
    }
    public void setObject(){
        objDrawerThread.obj[0] = new OBJ_Key(gp);
        objDrawerThread.obj[1] = (OBJ_Key)objDrawerThread.obj[0].cloneObject();
        objDrawerThread.obj[2] = (OBJ_Key)objDrawerThread.obj[0].cloneObject();

        objDrawerThread.obj[0].worldX = getGPTile();
        objDrawerThread.obj[0].worldY = getGPTile();

        objDrawerThread.obj[1].worldX = 60 * getGPTile();
        objDrawerThread.obj[1].worldY = 70 * getGPTile();

        objDrawerThread.obj[2].worldX = 30 * getGPTile();
        objDrawerThread.obj[2].worldY = 35 * getGPTile();

        objDrawerThread.obj[3] = new OBJ_Door(gp);
        objDrawerThread.obj[4] = (OBJ_Door) objDrawerThread.obj[3].cloneObject();

        objDrawerThread.obj[3].worldX = getGPTile();
        objDrawerThread.obj[3].worldY = 70 * getGPTile();

        objDrawerThread.obj[4].worldX = 60 * getGPTile();
        objDrawerThread.obj[4].worldY = getGPTile();

        objDrawerThread.obj[5] = new OBJ_Chest(gp);
        objDrawerThread.obj[5].worldX = 30 * getGPTile();
        objDrawerThread.obj[5].worldY = 70 * getGPTile();

        objDrawerThread.obj[6] = new OBJ_Orb(gp);
        objDrawerThread.obj[6].worldX = 31 * getGPTile();
        objDrawerThread.obj[6].worldY = 36 * getGPTile();

        objDrawerThread.obj[7] = new OBJ_Tornado(gp);
        objDrawerThread.obj[7].worldX = 19 * getGPTile();
        objDrawerThread.obj[7].worldY = 19 * getGPTile();
    }
}
