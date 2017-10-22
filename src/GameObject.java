import java.awt.*;

public abstract class GameObject {

    int x,y;
    protected ID id;
    protected int velX=0, velY=0;
    protected boolean allowMovement = true;
    protected int boardWidth, boardHeight;
    protected Handler handler;

    GameObject(int x, int y, Handler handler, ID id){
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.boardWidth = handler.getBoardWidth();
        this.boardHeight = handler.getBoardHeight();
        this.id = id;
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

    abstract int getWidth();

    abstract int getHeight();

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    ID getId() {
        return id;
    }

    void setId(ID id) {
        this.id = id;
    }

    int getVelX() {
        return velX;
    }

    void setVelX(int velX) {
        this.velX = velX;
    }

    int getVelY() {
        return velY;
    }

    void setVelY(int velY) {
        this.velY = velY;
    }
}
