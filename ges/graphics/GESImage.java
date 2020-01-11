package ges.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GESImage {

    private BufferedImage image;

    public void loadFromFile(String fileName) throws IOException {
        image = ImageIO.read(ClassLoader.getSystemResourceAsStream(fileName));
    }

    public BufferedImage getImage() {
        return image;
    }
}
