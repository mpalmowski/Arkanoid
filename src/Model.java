import java.awt.event.KeyEvent;

class Model {
    private Controller controller;
    private Menu menu;
    private static final String MENUBGPATH = "MenuBg.png";

    private Ranking ranking;
    private static final String RANKINGBGPATH = "RankingBg.png";

    private Game game;
    private static final String GAMEBGPATH = "RetroBg.png";
    private static final String PADDLEPATH = "RetroPaddle.png";
    private static final String BALLPATH = "RetroBall.png";
    private State gameState;

    Model(Controller controller, Game game, Menu menu, Ranking ranking) {
        this.controller = controller;
        this.game = game;
        this.menu = menu;
        this.ranking = ranking;
    }

    void addMenuBackground() {
        Image backgroundImage = new Image(MENUBGPATH);
        menu.setBackGround(new Background(game, backgroundImage));
    }

    void addMenuButtons() {
        menu.addButtons();
    }

    void addRankingBackground(){
        Image backgroundImage = new Image(RANKINGBGPATH);
        ranking.setBackGround(new Background(game, backgroundImage));
    }

    void addGameObjects(){
        Image backgroundImage = new Image(GAMEBGPATH);
        game.setBackGround(new Background(game, backgroundImage));

        Image paddleImage = new Image(PADDLEPATH);
        game.addObject(new Paddle(game, ID.Paddle, paddleImage));

        Image ballImage = new Image(BALLPATH);
        game.addObject(new Ball(game, ID.Ball, ballImage));

        game.addBricks();
    }

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

    void handleKeyReleased(int keyCode) {
        switch (gameState) {
            case Game:
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                    GameObject tempObject = game.objects.get(0);
                    tempObject.setVelX(0.0);
                }
                break;
        }
    }

    void setPlayerName(String playerName){
        menu.setPlayerName(playerName);
    }

    void tick() {
        switch (gameState) {
            case Game:
                game.tick();
                if(!game.running){
                    controller.gameOver();
                }
                break;
        }
    }

    void reset(){
        game.reset();
    }

    void setGameState(State gameState) {
        this.gameState = gameState;
    }
}
