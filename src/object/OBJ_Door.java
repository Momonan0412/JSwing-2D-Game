package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject {
    public OBJ_Door(){
        images = new BufferedImage[3];
        super.name = "Door";
        try{
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Object/door" + ( i + 1 ) + ".png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        super.collision = true;
    }
    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Door) super.cloneObject();
    }
}
