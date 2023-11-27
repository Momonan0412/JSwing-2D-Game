package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.getGPTile;

public class OBJ_Chest extends SuperObject {
    BufferedImage[] imgs;
    public OBJ_Chest(){
        imgs = new BufferedImage[2];
        images = new BufferedImage[2];
        super.name = "Chest";
        try{
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Object/chest" + ( i + 1 ) + ".png")));
                this.imgs[i] = utilityTool.scaleImage(images[i], getGPTile(), getGPTile());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage[] getImages() {
        return imgs;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Chest) super.cloneObject();
    }
}
