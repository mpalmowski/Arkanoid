class Model {
    private static final String BGPATH = "RetroBg.png";
    private static final String PADDLEPATH = "RetroPaddle.png";
    private static final String BALLPATH = "RetroBall.png";
    private static final int BRICKROWS = 5, BRICKSINROW = 9;
    Handler handler;
    private double breakBetweenBricks, leftMargin, upperMargin, brickWidth, brickHeight;

    Model(Handler handler) {
        this.handler = handler;
    }

    void addBackground(){
        Image backgroundImage = new Image(BGPATH);
        handler.setBackGround(new Background(handler, backgroundImage));
    }

    void addPaddle(){
        Image paddleImage = new Image(PADDLEPATH);
        handler.addObject(new Paddle(handler, ID.Paddle, paddleImage));
    }

    void addBall(){
        Image ballImage = new Image(BALLPATH);
        handler.addObject(new Ball(handler, ID.Ball, ballImage));
    }

    void calculateBricksPositions(){
        leftMargin = handler.getBoardWidth()/25;
        upperMargin = handler.getBoardHeight()/8;
        breakBetweenBricks = handler.getBoardWidth()/150;
        brickWidth = (handler.getBoardWidth() - (BRICKSINROW+1)*breakBetweenBricks - 2*leftMargin)/BRICKSINROW;
        brickHeight = brickWidth/2;
    }

    void addBricks(){
        for(Integer i=1; i<=BRICKROWS; i++){
            for (int j=1; j<=BRICKSINROW; j++){
                String filename = "Brick" + i.toString() + ".png";
                Image brickImage = new Image(filename);
                Brick brick = new Brick(brickWidth, brickHeight, handler, ID.Brick, brickImage);
                double brickX = (j-1)*brickWidth + j*breakBetweenBricks + leftMargin;
                double brickY = (i-1)*brickHeight + i*breakBetweenBricks + upperMargin;
                brick.setX(brickX);
                brick.setY(brickY);
                handler.addObject(brick);
            }
        }
    }

    void tick(){
        handler.tick();
    }
}
