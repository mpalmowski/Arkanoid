import java.awt.*;

abstract class GameObject {

    Double x = 0.0, y = 0.0;
    Double width =0.0, height = 0.0;
    ID id;
    double velX=0.0, velY=0.0;
    boolean allowMovement = true;
    double boardWidth, boardHeight;
    Handler handler;
    Image image;
    boolean exists = true;

    GameObject(Handler handler, ID id, Image image){
        this.handler = handler;
        this.boardWidth = handler.getBoardWidth();
        this.boardHeight = handler.getBoardHeight();
        this.id = id;
        this.image = image;
    }

    void setAllowMovement(boolean allowMovement) {
        this.allowMovement = allowMovement;
    }

    void tick(){
        this.x += velX;
        this.y += velY;
        collision();
        clamp();
    }

    abstract void render(Graphics graphics);

    abstract void clamp();

    abstract Rectangle getBounds();

    abstract void collision();

    abstract double getWidth();

    void vanish(){
        exists = false;
        x = 0.0;
        y = 0.0;
        width = 0.0;
        height = 0.0;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }

    void setVelX(double velX) {
        this.velX = velX;
    }
}
