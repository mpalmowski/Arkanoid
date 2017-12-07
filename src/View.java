import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends Canvas{
    private Integer width, height;
    private Handler handler;
    private Window window;

    View(Integer width, Integer height, Handler handler) {
        this.width = width;
        this.height = height;
        this.handler = handler;
    }

    void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    void createWindow() {
        window = new Window(width, height, "Arkanoid", this);
    }

    void getWindowParameters() {
        Insets windowInsets = window.getInsets();
        double boardHeight = height.doubleValue() - windowInsets.top - windowInsets.bottom;
        double boardWidth = width.doubleValue() - windowInsets.left - windowInsets.right;
        handler.setBoardDimensions(boardWidth, boardHeight);
    }
}
