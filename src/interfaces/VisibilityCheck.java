package interfaces;

import main.GamePanel;

public interface VisibilityCheck {
    default boolean isVisible(int worldX, int worldY, GamePanel gp) {
        /**dding this ( - || + ) gp.tileSize
         **Would erase the black but what if "additional" design in layer 0? or the very first layer will be a background? */
        return worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
    }
}