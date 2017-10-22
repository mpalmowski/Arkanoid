import java.awt.*;

class Brick extends GameObject{

    private static final int WIDTH = 100, HEIGHT = 50;

    Brick(int x, int y, Handler handler, ID id) {
        super(x, y, handler, id);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void clamp() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    void collision() {

    }

    @Override
    int getWidth() {
        return WIDTH;
    }

    @Override
    int getHeight() {
        return HEIGHT;
    }
}
