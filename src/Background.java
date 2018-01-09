import java.awt.*;

/**
 * Background of a single AppPhase. Displays an image on a whole window.
 */
class Background {

    private Double width =0.0, height = 0.0;
    private Image image;

    /**
     * Background constructor.
     * @param appPhase in which phase of an application will it be displayed
     * @param image specified image
     */
    Background(AppPhase appPhase, Image image){
        this.width = appPhase.getWindowWidth();
        this.height = appPhase.getWindowHeight();
        this.image = image;
    }

    void render(Graphics graphics){
        Double x = 0.0;
        Double y = 0.0;
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

}
