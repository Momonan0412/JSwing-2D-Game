package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Tornado extends SuperObject{
    public OBJ_Tornado() {
        images = new BufferedImage[8];
        super.name = "Tornado";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/Object/t" + (i+1) + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Orb) super.cloneObject();
    }
}