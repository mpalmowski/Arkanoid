import java.awt.*;

import static java.lang.Math.*;

public class Ball extends GameObject {

    private static final int velocity = 8;
    private double angle = 0;
    private static final int WIDTH = 15, HEIGHT = 15;

    Ball(int x, int y, Handler handler, ID id) {
        super(x, y, handler, id);

        allowMovement = false;
    }

    private double calculateAngle(GameObject paddle){
        int ballCenter = x + WIDTH/2;
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
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void clamp() {
        if(x > boardWidth - WIDTH){
            x = boardWidth - WIDTH;
            velX *= -1;
            angle *= -1;
        }
        else if(x < 0){
            x = 0;
            velX *= -1;
            angle *= -1;
        }

        if(y > boardHeight - HEIGHT){
            y = boardHeight - HEIGHT;
            velY *= -1;
        }
        else if(y < 0){
            y = 0;
            velY *= -1;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    void collision() {
        for (GameObject tempObject : handler.objects) {
            if(tempObject.id == ID.Paddle){
                if(getBounds().intersects(tempObject.getBounds())){
                    double angle = calculateAngle(tempObject);
                    this.angle += angle;
                    if(this.angle > 45.0)
                        this.angle = 45.0;
                    else if(this.angle < -45.0)
                        this.angle = -45.0;

                    Double tempX, tempY;
                    tempX = velocity * sin(this.angle);
                    tempY = -1 * velocity * cos(this.angle);
                    velX = tempX.intValue();
                    velY = tempY.intValue();
                    y = tempObject.y - HEIGHT;
                }
            }
            else if(tempObject.id != ID.Ball){
                if(getBounds().intersects(tempObject.getBounds())){
                    Rectangle tempRectangle = getBounds().intersection(tempObject.getBounds());
                    if(tempRectangle.x > tempRectangle.y){
                        velX *= -1;
                    }
                    else if(tempRectangle.y > tempRectangle.x){
                        velY *= -1;
                    }
                    else{
                        velX *= -1;
                        velY *= -1;
                    }
                }
            }
        }
    }

    @Override
    int getWidth() {
        return WIDTH;
    }

    @Override
    int getHeight() {
        return HEIGHT;
    }
}
