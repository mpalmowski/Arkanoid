import java.awt.*;
import java.util.LinkedList;

class Menu {
    private Background background = null;
    private double boardWidth = 0.0, boardHeight = 0.0;
    LinkedList<SimpleButton> buttons = new LinkedList<>();

    void setBackGround(Background background) {
        this.background = background;
    }

    void addButton(SimpleButton button) {
        this.buttons.add(button);
    }

    void setBoardDimensions(double boardWidth, double boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    void tick() {

    }

    void render(Graphics graphics) {
        if (background != null)
            background.render(graphics);

        graphics.setColor(Color.WHITE);

        for (SimpleButton button : buttons) {
            if (button.exists) {
                button.render(graphics);
            }
        }
    }
}
