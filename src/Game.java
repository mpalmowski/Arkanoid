import java.awt.*;
import java.util.LinkedList;

class Game {
    private Background background = null;
    LinkedList<GameObject> objects = new LinkedList<>();

    private double breakBetweenBricks, sideBrickMargin, upperBrickMargin, brickWidth, brickHeight;
    private static final int BRICKROWS = 5, BRICKSINROW = 9;

    boolean running = true;
    private double boardWidth = 0.0, boardHeight = 0.0;
    private int score = 0;

    void addObject(GameObject object){
        this.objects.add(object);
    }

    void setBackGround(Background background){
        this.background = background;
    }

    private void calculateBricksPositions(double windowWidth, double windowHeight) {
        sideBrickMargin = windowWidth / 25;
        upperBrickMargin = windowHeight / 8;
        breakBetweenBricks = windowWidth / 150;
        brickWidth = (windowWidth - (BRICKSINROW + 1) * breakBetweenBricks - 2 * sideBrickMargin) / BRICKSINROW;
        brickHeight = brickWidth / 2;
    }

    void addBricks(double windowWidth, double windowHeight){
        calculateBricksPositions(windowWidth, windowHeight);

        for (Integer i = 1; i <= BRICKROWS; i++) {
            for (int j = 1; j <= BRICKSINROW; j++) {
                String filename = "Brick" + i.toString() + ".png";
                Image brickImage = new Image(filename);
                Brick brick = new Brick(brickWidth, brickHeight, 100, this, ID.Brick, brickImage);
                double brickX = (j - 1) * brickWidth + j * breakBetweenBricks + sideBrickMargin;
                double brickY = (i - 1) * brickHeight + i * breakBetweenBricks + upperBrickMargin;
                brick.setX(brickX);
                brick.setY(brickY);
                addObject(brick);
            }
        }
    }

    void tick(){
        for (GameObject tempObject : objects) {
            tempObject.tick();
        }
    }

    void render(Graphics graphics){
        if(background != null)
            background.render(graphics);

        for (GameObject tempObject : objects) {
            if(tempObject.exists)
                tempObject.render(graphics);
        }
    }

    double getBoardWidth() {
        return boardWidth;
    }

    void setBoardDimensions(double boardWidth, double boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    double getBoardHeight() {
        return boardHeight;
    }

    void increaseScore(int scoredPoints){
        score += scoredPoints;
    }

    int getScore(){
        return score;
    }

    void reset(){
        for (GameObject tempObject : objects) {
            tempObject.reset();
        }
        running = true;
    }

    void end(){
        running = false;
    }
}
