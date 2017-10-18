import java.awt.*;

public class Ball extends GameObject {

    private static final int velocity = 8;

    Ball(int x, int y, ID id) {
        super(x, y, id);

        allowMovement = false;
    }

    @Override
    public void tick() {
        if(allowMovement && velX==0 && velY==0)
            velY = velocity;
        super.tick();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(x, y, 10, 10);
    }
}
