import java.awt.*;
import java.util.LinkedList;

class Handler {

    LinkedList<GameObject> objects = new LinkedList<>();
    private int boardWidth = 0, boardHeight = 0;

    void addObject(GameObject object){
        this.objects.add(object);
    }

    void removeObject(GameObject object){
        this.objects.remove(object);
    }

    void tick(){
        for (GameObject tempObject : objects) {
            tempObject.tick();
        }
    }

    void render(Graphics graphics){
        for (GameObject tempObject : objects) {
            tempObject.render(graphics);
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }
}
