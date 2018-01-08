import java.awt.*;

abstract class GameObject {

    Double x = 0.0, y = 0.0;
    Double width = 0.0, height = 0.0;
    ID id;
    double velX = 0.0, velY = 0.0;
    boolean allowMovement = true;
    double boardWidth, boardHeight;
    Game game;
    Image image;
    boolean exists = true;
    int scorePoints = 0;

    GameObject(Game game, ID id, Image image) {
        this.game = game;
        this.boardWidth = game.getWindowWidth();
        this.boardHeight = game.getWindowHeight();
        this.id = id;
        this.image = image;
    }

    void setAllowMovement(boolean allowMovement) {
        this.allowMovement = allowMovement;
    }

    void tick() {
        this.x += velX;
        this.y += velY;
        collision();
        clamp();
    }

    abstract void render(Graphics graphics);

    abstract void clamp();

    Rectangle getBounds(){
        if(exists == true)
            return new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());
        else
            return new Rectangle(0,0,0,0);
    }

    abstract void collision();

    double getWidth(){
        return width;
    }

    void vanish() {
        exists = false;
        game.increaseScore(scorePoints);
    }

    abstract void reset();

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
