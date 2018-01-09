import java.awt.*;
import java.awt.event.KeyEvent;

public class Controller implements Runnable {

    private static final Integer WIDTH = 450, HEIGHT = 600;
    private static final String rankingFileName = "C:\\Users\\palma\\Desktop\\Programming\\workspace\\IntelliJ\\Arkanoid\\res\\Ranking.txt";
    private Thread thread;
    private View view;
    private Model model;
    private Game game;
    private Menu menu;
    private boolean running = false;
    private State gameState = State.Menu;
    private String playerName = "PLAYER1";
    private Ranking ranking;

    Controller() {
        game = new Game();
        menu = new Menu();

        ranking = new Ranking(rankingFileName);

        view = new View(this, game, menu, ranking);
        model = new Model(this, game, menu, ranking);

        view.createWindow();
        view.getWindowParameters();

        view.addKeyListener(new KeyListener(this));
        view.addMouseListener(new MouseListener(this));

        view.setGameState(gameState);
        model.setGameState(gameState);

        addObjects();

        start();
    }

    private void setGameState(State gameState) {
        this.gameState = gameState;
        view.setGameState(gameState);
        model.setGameState(gameState);
    }

    void gameOver() {
        setGameState(State.GameOver);
        ranking.updateScore(playerName, game.getScore());
        ranking.save();
    }

    private void addObjects() {
        model.addMenuBackground();
        view.render();

        model.addMenuButtons();
        model.setPlayerName(playerName);
        view.render();

        model.addRankingBackground();

        model.addGameObjects();
    }

    void setWindowDimensions(double windowWidth, double windowHeight) {
        menu.setWindowDimensions(windowWidth, windowHeight);
        game.setWindowDimensions(windowWidth, windowHeight);
        ranking.setWindowDimensions(windowWidth, windowHeight);
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void run() {
        long before;
        long now;
        double ns = 1000000000 / 120.0;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int framesPerSecond = 0;

        before = System.nanoTime();
        while (running) {
            now = System.nanoTime();
            delta += (now - before) / ns;
            before = now;

            while (delta >= 1) {
                model.tick();
                delta--;
            }

            view.render();
            framesPerSecond++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }
        stop();
    }

    void handleMousePressed(int x, int y) {
        for (SimpleButton button : menu.buttons) {
            Rectangle buttonBounds = button.getBounds();
            if (buttonBounds.contains(x, y)) {
                switch (button.getButtonFunction()) {
                    case PlayerName:
                        setGameState(State.NameChanging);
                        model.setPlayerName(playerName + "|");
                        break;
                    case Start:
                        model.reset();
                        ranking.addPlayer(playerName);
                        setGameState(State.Game);
                        break;
                    case Ranking:
                        setGameState(State.Ranking);
                        break;
                }
            }
        }
    }

    void handleKeyPressed(int keyCode) {
        switch (gameState) {
            case Game:
                model.handleKeyPressed(keyCode);
                break;
            case NameChanging:
                if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    if (playerName.length() > 0) {
                        playerName = playerName.substring(0, playerName.length() - 1);
                    }
                    model.setPlayerName(playerName + "|");
                } else if (keyCode == KeyEvent.VK_ENTER) {
                    model.setPlayerName(playerName);
                    setGameState(State.Menu);
                } else if (playerName.length() < 10) {
                    if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
                        playerName = playerName.concat(KeyEvent.getKeyText(keyCode));
                        model.setPlayerName(playerName + "|");
                    } else if (keyCode == KeyEvent.VK_SPACE) {
                        playerName = playerName.concat(" ");
                        model.setPlayerName(playerName + "|");
                    }
                }
                break;
            case GameOver:
                if (keyCode == KeyEvent.VK_ENTER)
                    setGameState(State.Menu);
                break;
            case Ranking:
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    setGameState(State.Menu);
                }
                break;
        }
    }

    void handleKeyReleased(int keyCode) {
        model.handleKeyReleased(keyCode);
    }

    private synchronized void stop() {
        try {
            running = false;
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static Integer getWIDTH() {
        return WIDTH;
    }

    static Integer getHEIGHT() {
        return HEIGHT;
    }
}
