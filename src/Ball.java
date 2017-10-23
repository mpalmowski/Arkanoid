import java.awt.*;

import static java.lang.Math.*;

public class Ball extends GameObject {

    private static final int velocity = 7;
    private double angle = 0;

    Ball(Handler handler, ID id, Image image) {
        super(handler, id, image);

        this.width = handler.getBoardWidth()/42;
        this.height = this.width;
        this.x = handler.getBoardWidth()/2 - width/2;
        this.y = handler.getBoardWidth()*2/3;

        allowMovement = false;
    }

    private double calculateAngle(GameObject paddle){
        int ballCenter = x + width /2;
        int paddleCenter = paddle.getX() + paddle.getWidth()/2;
        double pointOfCollision = ballCenter - paddleCenter;
        double radius = paddle.getWidth() * sqrt(2) / 2;
        double sinus = pointOfCollision/radius;
        return asin(sinus);
    }

    @Override
    public void tick() {
        if(allowMovement && velX==0 && velY==0)
            velY = velocity;
        super.tick();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void clamp() {
        if(x > boardWidth - width){
            x = boardWidth - width;
            velX *= -1;
            angle *= -1;
        }
        else if(x < 0){
            x = 0;
            velX *= -1;
            angle *= -1;
        }

        if(y > boardHeight - height){
            y = boardHeight - height;
            velY *= -1;
        }
        else if(y < 0){
            y = 0;
            velY *= -1;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    void collision() {
        for (GameObject tempObject : handler.objects) {
            if(tempObject.id == ID.Paddle){
                if(getBounds().intersects(tempObject.getBounds())){
                    double angle = calculateAngle(tempObject);
                    this.angle += angle;
                    if(this.angle > 0.8)
                        this.angle = 0.8;
                    else if(this.angle < -0.8)
                        this.angle = -0.8;

                    Double tempX, tempY;
                    tempX = velocity * sin(this.angle);
                    tempY = -1 * velocity * cos(this.angle);
                    velX = tempX.intValue();
                    velY = tempY.intValue();
                    y = tempObject.y - height;
                }
            }
            else if(tempObject.id != ID.Ball){
                if(getBounds().intersects(tempObject.getBounds())){
                    Rectangle tempRectangle = getBounds().intersection(tempObject.getBounds());
                    if(tempRectangle.height > tempRectangle.width){
                        velX *= -1;
                    }
                    else if(tempRectangle.width > tempRectangle.height){
                        velY *= -1;
                    }
                    else{
                        velX *= -1;
                        velY *= -1;
                    }
                    tempObject.vanish();
                }
            }
        }
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
