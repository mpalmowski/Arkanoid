import java.awt.*;

public class Paddle extends GameObject{

    public Paddle(Handler handler, ID id, Image image) {
        super(handler, id, image);
        this.width = handler.getBoardWidth()/6;
        this.height = this.width/5;
        this.x = handler.getBoardWidth()/2 - width/2;
        this.y = handler.getBoardHeight() - height - 2;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void clamp() {
        if(x > boardWidth - width)
            x = boardWidth - width;
        else if(x < 0)
            x = 0;

        if(y > boardHeight - height)
            y = boardHeight - height;
        else if(y < 0)
            y = 0;
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
