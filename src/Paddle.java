import java.awt.*;

public class Paddle extends GameObject{

    private static final int WIDTH = 100, HEIGHT = 20;

    public Paddle(int x, int y, Handler handler, ID id) {
        super(x, y, handler, id);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void clamp() {
        if(x > boardWidth - WIDTH)
            x = boardWidth - WIDTH;
        else if(x < 0)
            x = 0;

        if(y > boardHeight - HEIGHT)
            y = boardHeight - HEIGHT;
        else if(y < 0)
            y = 0;
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
