import java.awt.*;

class Brick extends GameObject{

    Brick(Handler handler, ID id, Image image) {
        super(handler, id, image);

        this.width = handler.getBoardWidth()/8;
        this.height = this.width / 2;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void clamp() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    void collision() {

    }

    @Override
    int getWidth() {
        return width;
    }

    @Override
    int getHeight() {
        return height;
    }
}
