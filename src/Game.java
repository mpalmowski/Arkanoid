import java.awt.*;
import java.util.LinkedList;

/**
 * Phase of application, in which the actual game happens.
 * Consists of a paddle, a ball and a fixed number of bricks.
 * Movement of a paddle is controlled by keyboard.
 * Stops after a signal from a ball, that the game ended.
 */
class Game extends AppPhase {
    LinkedList<GameObject> objects = new LinkedList<>();
    private static final String BG_PATH = "RetroBg.png";
    private double breakBetweenBricks, sideBrickMargin, upperBrickMargin, brickWidth, brickHeight;
    private static final int BRICK_ROWS = 5, BRICKS_IN_ROW = 9, BRICK_POINTS = 100;

    boolean running = true;
    private int score = 0, roundScore = 0;

    @Override
    void setBackground() {
        Image backgroundImage = new Image(BG_PATH);
        this.background = new Background(this, backgroundImage);
    }

    /**
     * Adds a new object to objects list.
     *
     * @param object Specified object
     */
    void addObject(GameObject object) {
        this.objects.add(object);
    }

    /**
     * Calculates width, height and breaks between bricks to be placed, according to side and upper margins.
     */
    private void calculateBricksDimensions() {
        sideBrickMargin = windowWidth / 25;
        upperBrickMargin = windowHeight / 8;
        breakBetweenBricks = windowWidth / 150;
        brickWidth = (windowWidth - (BRICKS_IN_ROW + 1) * breakBetweenBricks - 2 * sideBrickMargin) / BRICKS_IN_ROW;
        brickHeight = brickWidth / 2;
    }

    /**
     * Calculates the position of each bricks, creates it and adds it to the objects list.
     */
    void addBricks() {
        calculateBricksDimensions();

        for (Integer i = 1; i <= BRICK_ROWS; i++) {
            for (int j = 1; j <= BRICKS_IN_ROW; j++) {
                String filename = "Brick" + i.toString() + ".png";
                Image brickImage = new Image(filename);
                Brick brick = new Brick(brickWidth, brickHeight, BRICK_POINTS, this, ID.Brick, brickImage);
                double brickX = (j - 1) * brickWidth + j * breakBetweenBricks + sideBrickMargin;
                double brickY = (i - 1) * brickHeight + i * breakBetweenBricks + upperBrickMargin;
                brick.setX(brickX);
                brick.setY(brickY);
                addObject(brick);
            }
        }
    }

    void tick() {
        for (GameObject tempObject : objects) {
            tempObject.tick();
        }
    }

    /**
     * Increases users score by the specified amount of points.
     * Detects when all bricks are destroyed and if so, calls resetBricks function.
     *
     * @param scoredPoints Specified amount of points
     */
    void increaseScore(int scoredPoints) {
        score += scoredPoints;
        roundScore += scoredPoints;
        if (roundScore >= (BRICK_ROWS * BRICKS_IN_ROW * BRICK_POINTS)) {
            roundScore = 0;
            nextRound();
        }
    }

    int getScore() {
        return score;
    }

    /**
     * Resets all objects to their default state.
     */
    void reset() {
        score = 0;
        roundScore = 0;
        for (GameObject tempObject : objects) {
            tempObject.reset();
        }
        running = true;
    }

    /**
     * Resets all bricks and a ball to their default state.
     */
    private void nextRound() {
        for (GameObject tempObject : objects) {
            if (tempObject.id != ID.Paddle)
                tempObject.reset();
        }
    }

    /**
     * Ends game.
     */
    void end() {
        running = false;
    }

    @Override
    void render(Graphics graphics) {
        if (background != null)
            background.render(graphics);

        for (GameObject tempObject : objects) {
            if (tempObject.exists)
                tempObject.render(graphics);
        }
    }
}
