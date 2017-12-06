import java.awt.*;
import java.awt.image.BufferStrategy;

public class Controller extends Canvas implements Runnable{

    private static final Integer WIDTH = 480, HEIGHT = 600;
    private Thread thread;
    private Handler handler;
    private boolean running = false;
    private Insets windowInsets;
    private double boardWidth, boardHeight;

    Controller(){
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        Window window = new Window(WIDTH, HEIGHT, "Arkanoid", this);

        windowInsets = window.getInsets();
        getBoardDimensions();
        handler.setBoardWidth(boardWidth);
        handler.setBoardHeight(boardHeight);

        addObjects();

        start();
    }

    private void getBoardDimensions(){
        boardHeight = HEIGHT.doubleValue() - windowInsets.top - windowInsets.bottom;
        boardWidth = WIDTH.doubleValue() - windowInsets.left - windowInsets.right;
    }

    private void addObjects(){
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

    private synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void run(){
        long before;
        long now;
        double ns = 1000000000/60.0;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int framesPerSecond = 0;

        before = System.nanoTime();
        while (running){
            now = System.nanoTime();
            delta += (now - before)/ns;
            before = now;

            while(delta >= 1){
                tick();
                delta--;
            }

            render();
            framesPerSecond ++;

            if(System.currentTimeMillis() - timer > 1000){
                timer = System.currentTimeMillis();
                System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }

    private void render(){
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

    private synchronized  void stop(){
        try{
            thread.join();
            running = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
