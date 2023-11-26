package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Orb extends SuperObject  {
    public OBJ_Orb() {
        images = new BufferedImage[36];
        super.name = "Orb";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/Object/" + (i) + ".png")));
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