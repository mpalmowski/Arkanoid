import java.awt.event.KeyEvent;

/**
 * Class responsible for all in-app calculations.
 */
class Model {
    private Controller controller;
    private Menu menu;
    private Ranking ranking;
    private Game game;
    private static final String PADDLE_PATH = "RetroPaddle.png";
    private static final String BALL_PATH = "RetroBall.png";
    private State appState;

    Model(Controller controller, Game game, Menu menu, Ranking ranking) {
        this.controller = controller;
        this.game = game;
        this.menu = menu;
        this.ranking = ranking;
    }

    /**
     * adds a background and buttons to the menu application phase.
     */
    void addMenuObjects() {
        menu.setBackground();
        menu.addButtons();
    }

    /**
     * Adds a background to the ranking application phase.
     */
    void addRankingBackground() {
        ranking.setBackground();
    }

    /**
     * Adds a background, paddle, ball and bricks to the game application phase.
     */
    void addGameObjects() {
        game.setBackground();

        Image paddleImage = new Image(PADDLE_PATH);
        game.addObject(new Paddle(game, paddleImage));

        Image ballImage = new Image(BALL_PATH);
        game.addObject(new Ball(game, ballImage));

        game.addBricks();
    }

    /**
     * Function called by the controller after a keyboard key being pressed.
     * Suitable only for the game application phase.
     * Starts moving the paddle left or right, or allows ball movement, depending on the key pressed.
     *
     * @param keyCode Specified keyCode
     */
    void handleKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            GameObject tempObject = game.objects.get(0);
            tempObject.setVelX(-2.5);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            GameObject tempObject = game.objects.get(0);
            tempObject.setVelX(2.5);
        } else if (game.running) {
            GameObject tempObject = game.objects.get(1);
            tempObject.setAllowMovement(true);
        }
    }

    /**
     * Function called by the controller after a keyboard key being pressed.
     * Suitable only for the game application phase.
     * Stops moving the paddle depending on the key pressed.
     *
     * @param keyCode Specified keyCode
     */
    void handleKeyReleased(int keyCode) {
        switch (appState) {
            case Game:
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                    GameObject tempObject = game.objects.get(0);
                    tempObject.setVelX(0.0);
                }
                break;
        }
    }

    void setPlayerName(String playerName) {
        menu.setPlayerName(playerName);
    }

    /**
     * Function called 120 times per second. Independent from rendering speed.
     */
    void tick() {
        switch (appState) {
            case Game:
                game.tick();
                if (!game.running) {
                    controller.gameOver();
                }
                break;
        }
    }

    /**
     * Resets the game application phase and all its objects to their default state.
     */
    void reset() {
        game.reset();
    }

    void setAppState(State appState) {
        this.appState = appState;
    }
}
