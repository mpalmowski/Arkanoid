import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends Canvas {
    private Integer width, height;
    private Game game;
    private Menu menu;
    private Window window;
    private State gameState;

    View(Integer width, Integer height, Game game, Menu menu) {
        this.width = width;
        this.height = height;
        this.game = game;
        this.menu = menu;
    }

    void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        switch (gameState) {
            case Game:
                game.render(graphics);
                graphics.setColor(Color.WHITE);
                String scoreOutput = "Score     " + game.getScore();
                graphics.drawString(scoreOutput, width / 50, height - height / 11);
                break;
            case Menu:
                menu.render(graphics);
                break;
        }

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
        game.setBoardDimensions(boardWidth, boardHeight);
        menu.setBoardDimensions(boardWidth, boardHeight);
    }

    void setGameState(State gameState) {
        this.gameState = gameState;
    }
}
