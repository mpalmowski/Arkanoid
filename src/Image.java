import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Image {

    private BufferedImage bufferedImage;

    Image(String path) {
        this.bufferedImage = loadImage(path);
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(path));
        }
        catch (Exception e){
            System.out.print("Loading image failed: " + e.getMessage());
        }
        return image;
    }

    BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
