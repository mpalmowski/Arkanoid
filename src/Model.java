class Model {
    private Menu menu;
    private static final String MENUBGPATH = "MenuBg.png";
    private static final int BUTTONS = 3;
    private double breakBetweenButtons, upperButtonMargin, lowerButtonMargin, sideButtonMargin;
    private Double buttonWidth, buttonHeight;
    private String[] buttonTexts = new String[]{
            "PLAYER 1",
            "START",
            "RANKING"
    };
    private Game game;
    private static final String GAMEBGPATH = "RetroBg.png";
    private static final String PADDLEPATH = "RetroPaddle.png";
    private static final String BALLPATH = "RetroBall.png";
    private static final int BRICKROWS = 5, BRICKSINROW = 9;
    private double breakBetweenBricks, sideBrickMargin, upperBrickMargin, brickWidth, brickHeight;
    private State gameState;

    Model(Game game, Menu menu) {
        this.game = game;
        this.menu = menu;
    }

    void addMenuBackground(){
        Image backgroundImage = new Image(MENUBGPATH);
        menu.setBackGround(new Background(game, backgroundImage));
    }

    void addMenuButtons(){
        breakBetweenButtons = game.getBoardHeight()/15;
        upperButtonMargin = game.getBoardHeight()/2.5;
        lowerButtonMargin = game.getBoardHeight()/10;
        sideButtonMargin = game.getBoardWidth()/4;
        buttonWidth = game.getBoardWidth() - 2*sideButtonMargin;
        buttonHeight = (game.getBoardHeight() - upperButtonMargin - lowerButtonMargin - (BUTTONS-1)*breakBetweenButtons)/BUTTONS;

        for (int i=0; i<BUTTONS; i++){
            Double buttonX, buttonY;
            buttonX = sideButtonMargin;
            buttonY = upperButtonMargin + i*buttonHeight + i*breakBetweenButtons;
            SimpleButton button = new SimpleButton(buttonX.intValue(), buttonY.intValue(), buttonWidth, buttonHeight, buttonTexts[i]);
            menu.addButton(button);
        }
    }

    void addGameBackground(){
        Image backgroundImage = new Image(GAMEBGPATH);
        game.setBackGround(new Background(game, backgroundImage));
    }

    void addPaddle(){
        Image paddleImage = new Image(PADDLEPATH);
        game.addObject(new Paddle(game, ID.Paddle, paddleImage));
    }

    void addBall(){
        Image ballImage = new Image(BALLPATH);
        game.addObject(new Ball(game, ID.Ball, ballImage));
    }

    void calculateBricksPositions(){
        sideBrickMargin = game.getBoardWidth()/25;
        upperBrickMargin = game.getBoardHeight()/8;
        breakBetweenBricks = game.getBoardWidth()/150;
        brickWidth = (game.getBoardWidth() - (BRICKSINROW+1)*breakBetweenBricks - 2* sideBrickMargin)/BRICKSINROW;
        brickHeight = brickWidth/2;
    }

    void addBricks(){
        for(Integer i=1; i<=BRICKROWS; i++){
            for (int j=1; j<=BRICKSINROW; j++){
                String filename = "Brick" + i.toString() + ".png";
                Image brickImage = new Image(filename);
                Brick brick = new Brick(brickWidth, brickHeight, 100, game, ID.Brick, brickImage);
                double brickX = (j-1)*brickWidth + j*breakBetweenBricks + sideBrickMargin;
                double brickY = (i-1)*brickHeight + i*breakBetweenBricks + upperBrickMargin;
                brick.setX(brickX);
                brick.setY(brickY);
                game.addObject(brick);
            }
        }
    }

    void tick(){
        if(gameState == State.Game)
            game.tick();
    }

    void setGameState(State gameState) {
        this.gameState = gameState;
    }
}
