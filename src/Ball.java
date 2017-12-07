import java.awt.*;

import static java.lang.Math.*;

public class Ball extends GameObject {

    private static final double velocity = 5;
    private double angle = 0;

    Ball(Handler handler, ID id, Image image) {
        super(handler, id, image);

        this.width = boardWidth/35;
        this.height = boardWidth/35;
        this.x = boardWidth/2 - width/2;
        this.y = boardHeight*2/3;

        allowMovement = false;
    }

    private double calculateAngle(GameObject paddle){
        double ballCenter = x + width /2;
        double paddleCenter = paddle.getX() + paddle.getWidth()/2;
        double pointOfCollision = ballCenter - paddleCenter;
        double radius = paddle.getWidth() * sqrt(2) / 2;
        double sinus = pointOfCollision/radius;
        return asin(sinus);
    }

    @Override
    public void tick() {
        if(allowMovement && velX==0 && velY==0)
            velY = velocity;
        else if(!allowMovement){
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
        if(x > boardWidth*29/30 - width){
            x = boardWidth*29/30 - width;
            velX *= -1;
            angle *= -1;
        }
        else if(x < boardWidth/30){
            x = boardWidth/30;
            velX *= -1;
            angle *= -1;
        }

        if(y > boardHeight - height){
            y = boardHeight - height;
            velY *= -1;
        }
        else if(y < boardHeight/24){
            y = boardHeight/24;
            velY *= -1;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());
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

                    double tempX, tempY;
                    tempX = velocity * sin(this.angle);
                    tempY = -1 * velocity * cos(this.angle);
                    velX = tempX;
                    velY = tempY;
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
    double getWidth() {
        return width;
    }
}
