import java.awt.*;
import java.awt.event.KeyEvent;

public class Controller implements Runnable {

    private static final Integer WIDTH = 450, HEIGHT = 600;
    private Thread thread;
    private View view;
    private Model model;
    private Menu menu;
    private boolean running = false;
    private State gameState = State.Menu;
    private String playerName = "PLAYER 1";

    Controller() {
        Game game = new Game();
        menu = new Menu();

        view = new View(WIDTH, HEIGHT, game, menu);
        model = new Model(this, game, menu);

        view.createWindow();
        view.getWindowParameters();

        view.addKeyListener(new KeyListener(this));
        view.addMouseListener(new MouseListener(this));

        view.setGameState(gameState);
        model.setGameState(gameState);

        addObjects();

        start();
    }

    private void setGameState(State gameState){
        this.gameState = gameState;
        view.setGameState(gameState);
        model.setGameState(gameState);
    }

    void gameOver(){
        setGameState(State.GameOver);
    }

    private void addObjects() {
        model.addMenuBackground();
        view.render();

        model.addMenuButtons();
        view.render();

        model.addGameBackground();
        view.render();

        model.addPaddle();
        view.render();

        model.addBall();
        view.render();

        model.calculateBricksPositions();
        model.addBricks();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void run() {
        long before;
        long now;
        double ns = 1000000000 / 60.0;
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
                        setGameState(State.Game);
                        break;
                    case Ranking:
                        break;
                }
            }
        }
    }

    void handleKeyPressed(int keyCode){
        switch (gameState) {
            case Game:
                model.handleKeyPressed(keyCode);
                break;
            case NameChanging:
                if(keyCode == KeyEvent.VK_BACK_SPACE){
                    if(playerName.length() > 0){
                        playerName = playerName.substring(0, playerName.length()-1);
                    }
                    model.setPlayerName(playerName + "|");
                }
                else if(keyCode == KeyEvent.VK_ENTER){
                    model.setPlayerName(playerName);
                    setGameState(State.Menu);
                }
                else if(keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z){
                    playerName = playerName.concat(KeyEvent.getKeyText(keyCode));
                    model.setPlayerName(playerName + "|");
                }
                break;
            case GameOver:
                setGameState(State.Menu);
                break;
        }
    }

    void handleKeyReleased(int keyCode){
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
}
