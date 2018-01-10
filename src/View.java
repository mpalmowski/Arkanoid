import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends Canvas {
    private Controller controller;
    private Game game;
    private Menu menu;
    private Ranking ranking;
    private Integer width, height;
    private Window window;
    private State appState;

    View(Controller controller, Game game, Menu menu, Ranking ranking) {
        this.controller = controller;
        this.width = Controller.getWIDTH();
        this.height = Controller.getHEIGHT();
        this.game = game;
        this.menu = menu;
        this.ranking = ranking;
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

        switch (appState) {
            case Game:
                game.render(graphics);
                graphics.setColor(Color.WHITE);
                String scoreOutput = "Score     " + game.getScore();
                graphics.drawString(scoreOutput, width / 50, height - height / 11);
                break;
            case Menu:
            case NameChanging:
                menu.render(graphics);
                break;
            case GameOver:
                game.render(graphics);
                graphics.setColor(Color.WHITE);
                scoreOutput = "Score     " + game.getScore();
                graphics.drawString(scoreOutput, width / 50, height - height / 11);
                final String textLine1 = "GAME   OVER";
                final String textLine2 = "PRESS   ENTER   TO   CONTINUE";
                double textWidth = graphics.getFontMetrics().getStringBounds(textLine1, graphics).getWidth();
                double textHeight = graphics.getFontMetrics().getStringBounds(textLine1, graphics).getHeight();
                Double textX = (width - textWidth)/2;
                Double textY = (height - textHeight)/2 + textHeight;
                graphics.drawString(textLine1, textX.intValue(), textY.intValue());
                textWidth = graphics.getFontMetrics().getStringBounds(textLine2, graphics).getWidth();
                textHeight = graphics.getFontMetrics().getStringBounds(textLine2, graphics).getHeight();
                textX = (width - textWidth)/2;
                textY += textHeight + width/10;
                graphics.drawString(textLine2, textX.intValue(), textY.intValue());
                break;
            case Ranking:
                ranking.render(graphics);
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
        controller.setWindowDimensions(boardWidth, boardHeight);
    }

    void setAppState(State appState) {
        this.appState = appState;
    }
}
