package object;

import interfaces.CloneableImageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {
    public OBJ_Key() {
        images = new BufferedImage[8];
        super.name = "Key";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/Object/key" + (i + 1) + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Key) super.cloneObject();
    }
}
