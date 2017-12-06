import java.awt.*;

class Background {

    private Double width =0.0, height = 0.0;
    private Image image;

    Background(Handler handler, Image image){
        this.width = handler.getBoardWidth();
        this.height = handler.getBoardHeight();
        this.image = image;
    }

    void render(Graphics graphics){
        Double x = 0.0;
        Double y = 0.0;
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

}
