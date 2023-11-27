package interfaces;

import main.GamePanel;

import static main.GamePanel.getGPTile;

public interface VisibilityCheck {
    default boolean isVisible(int worldX, int worldY, GamePanel gp) {
        /**dding this ( - || + ) gp.tileSize
         **Would erase the black but what if "additional" design in layer 0? or the very first layer will be a background? */
        return worldX + getGPTile() > gp.player.worldX - gp.player.screenX &&
                worldX - getGPTile() < gp.player.worldX + gp.player.screenX &&
                worldY + getGPTile() > gp.player.worldY - gp.player.screenY &&
                worldY - getGPTile() < gp.player.worldY + gp.player.screenY;
    }
}