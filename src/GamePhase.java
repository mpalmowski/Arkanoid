import java.awt.*;

abstract class GamePhase {
    Background background = null;
    double windowHeight = 0, windowWidth = 0;

    void setBackGround(Background background){
        this.background = background;
    }

    void setWindowDimensions(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    double getWindowHeight() {
        return windowHeight;
    }

    double getWindowWidth() {
        return windowWidth;
    }

    abstract void render(Graphics graphics);
}
