import java.awt.*;

import static java.lang.Math.*;

/**
 * Object representing a ball during the game.
 * When movement allowed, constantly moves around the screen, bouncing off other objects.
 * Stops when colliding with a bottom edge of a game board.
 * Destroys bricks on collision.
 * Movement angle controlled by the point of collision with a paddle.
 */
public class Ball extends GameObject {

    private static final double velocity = 2.5;
    private double angle = 0;

    /**
     * Creates a non-moving ball in a fixed point of a window. Can be set in motion by setting allowMovement to true;
     *
     * @param game  Specified Game class instance
     * @param image Specified image
     */
    Ball(Game game, Image image) {
        super(game, ID.Ball, image);

        this.width = boardWidth / 35;
        this.height = boardWidth / 35;
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight * 2 / 3;

        allowMovement = false;
    }

    /**
     * Calculates the angle of collision with a paddle.
     *
     * @param paddle colliding paddle
     * @return calculated angle in radians
     */
    private double calculateAngle(GameObject paddle) {
        double ballCenter = x + width / 2;
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
        double pointOfCollision = ballCenter - paddleCenter;
        return pointOfCollision / paddle.getWidth() * 1.5;
    }

    @Override
    public void tick() {
        if (allowMovement && velX == 0 && velY == 0)
            velY = velocity;
        else if (!allowMovement) {
            velX = 0;
            velY = 0;
        }
        super.tick();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width.intValue(), height.intValue(), null);
    }

    @Override
    public void clamp() {
        if (x > boardWidth * 29 / 30 - width) {
            x = boardWidth * 29 / 30 - width;
            velX *= -1;
            angle *= -1;
        } else if (x < boardWidth / 30) {
            x = boardWidth / 30;
            velX *= -1;
            angle *= -1;
        }

        if (y > boardHeight * 59 / 64 - height) {
            setAllowMovement(false);
            game.end();
        } else if (y < boardHeight / 24) {
            y = boardHeight / 24;
            velY *= -1;
        }
    }

    @Override
    void collision() {
        int vectorX = 1;
        int vectorY = 1;
        for (GameObject tempObject : game.objects) {
            if (tempObject.id == ID.Paddle) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    this.angle = calculateAngle(tempObject);

                    double tempX, tempY;
                    tempX = velocity * sin(this.angle);
                    tempY = -1 * velocity * cos(this.angle);
                    velX = tempX;
                    velY = tempY;
                    y = tempObject.y - height;
                    break;
                }
            } else if (tempObject.id != ID.Ball) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    Rectangle ballRectangle = getBounds();
                    Rectangle brickRectangle = tempObject.getBounds();
                    /*below*/
                    if (ballRectangle.getCenterY() >= brickRectangle.getMaxY()) {
                        vectorY = -1;
                    }
                    /*above*/
                    else if (ballRectangle.getCenterY() <= brickRectangle.getMinY()) {
                        vectorY = -1;
                    }
                    /*right*/
                    else if (ballRectangle.getCenterX() >= brickRectangle.getMaxX()) {
                        vectorX = -1;
                    }
                    /*left*/
                    else if (ballRectangle.getCenterX() <= brickRectangle.getMinX()) {
                        vectorX = -1;
                    }

                    tempObject.vanish();
                }
            }
        }
        velX *= vectorX;
        velY *= vectorY;
        if (vectorX == -1)
            angle *= -1;
    }

    @Override
    void reset() {
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight * 2 / 3;
        allowMovement = false;
    }

    @Override
    double getWidth() {
        return width;
    }
}
