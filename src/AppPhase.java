import java.awt.*;

/**
 * Properties, methods and objects of a current application phase.
 * Each phase contains a background and has information about the windows dimensions.
 */
abstract class AppPhase {
    Background background = null;
    double windowHeight = 0, windowWidth = 0;

    abstract void setBackground();

    /**
     * Set current window dimensions.
     * Doesn't include window borders and a title bar.
     *
     * @param windowWidth  Specified window width
     * @param windowHeight Specified window height
     */
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

    /**
     * Rendering function.
     *
     * @param graphics Specified graphics
     */
    abstract void render(Graphics graphics);
}
