import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Application controller. Called by the main function.
 */
public class Controller implements Runnable {

    private static final Integer WIDTH = 450, HEIGHT = 600;
    private static final String rankingFileName = "C:\\Users\\palma\\Desktop\\Programming\\workspace\\IntelliJ\\Arkanoid\\res\\Ranking.txt";
    private Thread thread;
    private View view;
    private Model model;
    private Game game;
    private Menu menu;
    private boolean running = false;
    private State appState = State.Menu;
    private String playerName = "PLAYER1";
    private Ranking ranking;

    /**
     * Creates the View and Model class objects.
     * Creates a ranking and reads the ranking file.
     * Creates a window and all game objects.
     * Starts the window application.
     */
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

        view.setAppState(appState);
        model.setAppState(appState);

        addObjects();

        start();
    }

    /**
     * Sets the appState to a specified value.
     * appState informs objects in which phase the app currently is.
     *
     * @param appState Specified appState
     */
    private void setAppState(State appState) {
        this.appState = appState;
        view.setAppState(appState);
        model.setAppState(appState);
    }

    /**
     * Function called when the ball touches the bottom border of the game board.
     * Saves user score and sets the appState.
     */
    void gameOver() {
        setAppState(State.GameOver);
        ranking.updateScore(playerName, game.getScore());
        ranking.save();
    }

    /**
     * Creates and adds objects to all game phases.
     */
    private void addObjects() {
        model.addMenuObjects();
        model.setPlayerName(playerName);
        view.render();

        model.addRankingBackground();

        model.addGameObjects();
    }

    /**
     * Informs all game phases about current window dimensions.
     * Doesn't include window borders and a title bar.
     *
     * @param windowWidth  Specified window width
     * @param windowHeight Specified window height
     */
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

    /**
     * Main loop of the application.
     * Calls the tick function 120 times per second.
     * Calls the render function as fast as possible and informs about current FPS amount.
     */
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

    /**
     * Function called by a listener after a mouse button being pressed.
     * Depending on the current game phase, calls a proper function.
     *
     * @param x X coordinate of mouse cursor
     * @param y Y coordinate of mouse cursor
     */
    void handleMousePressed(int x, int y) {
        for (SimpleButton button : menu.buttons) {
            Rectangle buttonBounds = button.getBounds();
            if (buttonBounds.contains(x, y)) {
                switch (button.getButtonFunction()) {
                    case PlayerName:
                        setAppState(State.NameChanging);
                        model.setPlayerName(playerName + "|");
                        break;
                    case Start:
                        model.reset();
                        ranking.addPlayer(playerName);
                        setAppState(State.Game);
                        break;
                    case Ranking:
                        setAppState(State.Ranking);
                        break;
                }
            }
        }
    }

    /**
     * Function called by a listener after a keyboard key being pressed.
     * Depending on the current game phase, calls a proper function.
     *
     * @param keyCode Specified keyCode
     */
    void handleKeyPressed(int keyCode) {
        switch (appState) {
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
                    setAppState(State.Menu);
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
                    setAppState(State.Menu);
                break;
            case Ranking:
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    setAppState(State.Menu);
                }
                break;
        }
    }

    /**
     * Function called by a listener after a keyboard key being released.
     * Calls models function.
     *
     * @param keyCode Specified keyCode
     */
    void handleKeyReleased(int keyCode) {
        model.handleKeyReleased(keyCode);
    }

    /**
     * Exits the application.
     */
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
