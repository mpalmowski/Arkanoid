import java.awt.*;

/**
 * Every object in the game (paddle, ball, brick).
 * Can be rendered or moved. Can collide with other objects.
 */
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

    /**
     * Creates an object assigned to specified Game class instance.
     * Object is represented by an image in a game window.
     *
     * @param game  Specified Game class instance
     * @param id    Specified id to distinguish from other objects
     * @param image Specified image
     */
    GameObject(Game game, ID id, Image image) {
        this.game = game;
        this.boardWidth = game.getWindowWidth();
        this.boardHeight = game.getWindowHeight();
        this.id = id;
        this.image = image;
    }

    /**
     * Specifies whether the object is allowed to move.
     *
     * @param allowMovement Specified boolean value
     */
    void setAllowMovement(boolean allowMovement) {
        this.allowMovement = allowMovement;
    }

    /**
     * Function called 120 times per second. Independent from rendering speed.
     */
    void tick() {
        this.x += velX;
        this.y += velY;
        collision();
        clamp();
    }

    /**
     * Rendering function.
     *
     * @param graphics Specified graphics
     */
    abstract void render(Graphics graphics);

    /**
     * Function making sure the object doesn't go beyond window borders.
     */
    abstract void clamp();

    /**
     * @return Rectangle of width, height and coordinates same as the object.
     */
    Rectangle getBounds() {
        if (exists)
            return new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());
        else
            return new Rectangle(0, 0, 0, 0);
    }

    /**
     * Checks for collisions with other objects.
     * Performs certain actions in case of collision.
     */
    abstract void collision();

    double getWidth() {
        return width;
    }

    /**
     * Makes an object invisible for the user.
     * Vanished object can't be collided with.
     */
    void vanish() {
        exists = false;
        game.increaseScore(scorePoints);
    }

    /**
     * Resets the object to it's default state.
     */
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
