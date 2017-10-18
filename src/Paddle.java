import java.awt.*;

public class Paddle extends GameObject{

    Paddle(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, 50, 10);
    }
}
