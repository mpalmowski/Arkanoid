import java.awt.*;

/**
 * Object representing a brick during game.
 * Displays an image of desired dimensions and in desired place of a window.
 * Can be destroyed after collision with a ball.
 */
class Brick extends GameObject {

    /**
     * Creates a brick in a specified point of a window.
     *
     * @param width       Specified width
     * @param height      Specified height
     * @param scorePoints Amount of points granted on destruction
     * @param game        Specified Game class instance
     * @param id          Specified id to distinguish from other objects
     * @param image       Specified image
     */
    Brick(double width, double height, int scorePoints, Game game, ID id, Image image) {
        super(game, id, image);

        this.scorePoints = scorePoints;
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
    void collision() {

    }

    @Override
    void reset() {
        exists = true;
    }
}
