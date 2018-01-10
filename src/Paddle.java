import java.awt.*;

/**
 * Object representing a paddle during the game.
 * Can be moved left or right, according to signals from the user.
 * The only object controlled by the user during the game.
 */
public class Paddle extends GameObject {
    /**
     * Creates a paddle at the bottom center of the game board.
     *
     * @param game  Specified game
     * @param id    Specified id to distinguish from other objects
     * @param image Specified image
     */
    Paddle(Game game, ID id, Image image) {
        super(game, id, image);
        this.width = game.getWindowWidth() / 6;
        this.height = this.width / 5;
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight - height - boardHeight / 11;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

    @Override
    public void clamp() {
        if (x > boardWidth * 29 / 30 - width)
            x = boardWidth * 29 / 30 - width;
        else if (x < boardWidth / 30)
            x = boardWidth / 30;
    }

    @Override
    void collision() {

    }

    @Override
    void reset() {
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight - height - boardHeight / 11;
    }

    @Override
    double getWidth() {
        return width;
    }
}
