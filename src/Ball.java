import java.awt.*;

import static java.lang.Math.*;

public class Ball extends GameObject {

    private static final double velocity = 5;
    private double angle = 0;

    Ball(Game game, ID id, Image image) {
        super(game, id, image);

        this.width = boardWidth / 35;
        this.height = boardWidth / 35;
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight * 2 / 3;

        allowMovement = false;
    }

    private double calculateAngle(GameObject paddle) {
        double ballCenter = x + width / 2;
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
        double pointOfCollision = ballCenter - paddleCenter;
        return pointOfCollision / paddle.getWidth();
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
                    double angle = calculateAngle(tempObject);
                    this.angle += angle;
                    if (this.angle > 0.8)
                        this.angle = 0.8;
                    else if (this.angle < -0.8)
                        this.angle = -0.8;

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
                    Rectangle tempRectangle = getBounds().intersection(tempObject.getBounds());
                    if(tempRectangle.height > tempRectangle.width){
                        vectorX = -1;
                    }
                    else if(tempRectangle.width > tempRectangle.height){
                        vectorY = -1;
                    }
                    else{
                        vectorX = -1;
                        vectorY = -1;
                    }
                    tempObject.vanish();
                }
            }
        }
        velX *= vectorX;
        velY *= vectorY;
    }

    @Override
    void reset() {
        this.x = boardWidth / 2 - width / 2;
        this.y = boardHeight * 2 / 3;
    }

    @Override
    double getWidth() {
        return width;
    }
}
