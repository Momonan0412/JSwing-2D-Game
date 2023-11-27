package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.getGPTile;

public class OBJ_Door extends SuperObject {
    BufferedImage[] imgs;
    public OBJ_Door(){
        imgs = new BufferedImage[3];
        images = new BufferedImage[3];
        super.name = "Door";
        try{
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Object/door" + ( i + 1 ) + ".png")));
                this.imgs[i] = utilityTool.scaleImage(images[i], getGPTile(), getGPTile());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        super.collision = true;
    }

    @Override
    public BufferedImage[] getImages() {
        return imgs;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Door) super.cloneObject();
    }
}
