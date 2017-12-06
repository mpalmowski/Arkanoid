import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends Canvas{
    private Integer width, height;
    private double boardWidth, boardHeight;
    private Handler handler;

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
        Window window = new Window(width, height, "Arkanoid", this);
        Insets windowInsets = window.getInsets();
        boardHeight = height.doubleValue() - windowInsets.top - windowInsets.bottom;
        boardWidth = width.doubleValue() - windowInsets.left - windowInsets.right;
        handler.setBoardDimensions(boardWidth, boardHeight);
    }

    void addObjects(){
        Image backgroundImage = new Image("RetroBg.png");
        handler.setBackGround(new Background(handler, backgroundImage));

        render();

        Image paddleImage = new Image("RetroPaddle.png");
        handler.addObject(new Paddle(handler, ID.Paddle, paddleImage));

        Image ballImage = new Image("RetroBall.png");
        handler.addObject(new Ball(handler, ID.Ball, ballImage));

        render();

        double breakBetweenBricks = boardWidth/140;
        double leftMargin = boardWidth/25;
        double upperMargin = boardHeight/6;
        double brickWidth = (boardWidth - 10*breakBetweenBricks - 2*leftMargin)/9;
        double brickHeight = brickWidth/2;
        for(Integer i=1; i<=5; i++){
            for (int j=1; j<=9; j++){
                String filename = "Brick" + i.toString() + ".png";
                Image brickImage = new Image(filename);
                Brick brick = new Brick(brickWidth, brickHeight, handler, ID.Brick, brickImage);
                double brickX = (j-1)*brickWidth + j*breakBetweenBricks + leftMargin;
                double brickY = (i-1)*brickHeight + i*breakBetweenBricks + upperMargin;
                brick.setX(brickX);
                brick.setY(brickY);
                handler.addObject(brick);
                render();
            }
        }
    }

    double getBoardWidth() {
        return boardWidth;
    }

    double getBoardHeight() {
        return boardHeight;
    }
}
