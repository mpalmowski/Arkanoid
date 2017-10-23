import java.awt.*;

class Brick extends GameObject{

    private int width = 100, height = 50;

    Brick(int x, int y, Handler handler, ID id, Image image) {
        super(handler, id, image);

        this.width = handler.getBoardWidth()/6;
        this.height = this.width / 2;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(x, y, width, height);
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
