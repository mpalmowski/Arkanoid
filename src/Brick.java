import java.awt.*;

class Brick extends GameObject{

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
