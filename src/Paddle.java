import java.awt.*;

public class Paddle extends GameObject{

    Paddle(Game game, ID id, Image image) {
        super(game, id, image);
        this.width = game.getBoardWidth()/6;
        this.height = this.width/5;
        this.x = boardWidth/2 - width/2;
        this.y = boardHeight - height - boardHeight/11;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

    @Override
    public void clamp() {
        if(x > boardWidth*29/30 - width)
            x = boardWidth*29/30 - width;
        else if(x < boardWidth/30)
            x = boardWidth/30;
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
