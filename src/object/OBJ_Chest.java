package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest(){
        images = new BufferedImage[2];
        super.name = "Chest";
        try{
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Object/chest" + ( i + 1 ) + ".png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Chest) super.cloneObject();
    }
}
