package object;

import interfaces.CloneableImageObject;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.getGPTile;

public class OBJ_Orb extends SuperObject  {
    BufferedImage[] imgs;
    public OBJ_Orb(GamePanel gp) {
        super(gp);
        imgs = new BufferedImage[36];
        images = new BufferedImage[36];
        super.name = "Orb";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/Object/" + (i) + ".png")));
                this.imgs[i] = utilityTool.scaleImage(images[i], getGPTile(), getGPTile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage[] getImages() {
        return imgs;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Orb) super.cloneObject();
    }
}