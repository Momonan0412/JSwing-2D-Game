package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage[] image;
    public boolean collision = false;
    public Tile imageSetter(int size){
        image = new BufferedImage[size];
        return this;
    }
}
