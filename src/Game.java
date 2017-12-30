import java.awt.*;
import java.util.LinkedList;

class Game {
    public boolean running = true;

    private Background background = null;
    LinkedList<GameObject> objects = new LinkedList<>();
    private double boardWidth = 0.0, boardHeight = 0.0;
    private int score = 0;

    void addObject(GameObject object){
        this.objects.add(object);
    }

    void setBackGround(Background background){
        this.background = background;
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
