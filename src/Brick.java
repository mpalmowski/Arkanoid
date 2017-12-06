import java.awt.*;

class Brick extends GameObject{

    Brick(double width, double height, Handler handler, ID id, Image image) {
        super(handler, id, image);

        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

    @Override
    public void clamp() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());
    }

    @Override
    void collision() {

    }

    @Override
    double getWidth() {
        return width;
    }
}
